package com.readlearncode.view;

import com.readlearncode.model.Book;
import com.readlearncode.model.Order;
import com.readlearncode.model.OrderLine;
import com.readlearncode.service.ShoppingCart;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Named
@Stateful
@ConversationScoped
public class CartBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ShoppingCart shoppingCart;

    @Inject
    private Conversation conversation;

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext(unitName = "getting-started-with-enterprise-javabeans-persistence-unit", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private Book book;
    private List<OrderLine> orderLines;
    private Float totalOrder;
    private Integer quantity = 0;
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Float getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Float totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void create() {

        this.conversation.begin();
        this.conversation.setTimeout(1800000L);
        id = shoppingCart.getOrder().getId();
        // return "create?faces-redirect=true";
    }

    public void retrieve() {

        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
            this.conversation.setTimeout(1800000L);
        }

        if (this.id == null) {
            shoppingCart.initializeCart();
            id = shoppingCart.getOrder().getId();
        }

        this.orderLines = shoppingCart.getOrder().getOrderLines();
        this.totalOrder = shoppingCart.getOrder().getTotalOrder();

    }

    public String add(Book book) {
        shoppingCart.addBook(book, quantity);
        return "search?faces-redirect=true";
    }


    private int page;
    private long count;
    private List<Order> pageItems;


    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
    }

    public String search() {
        this.page = 0;
        return null;
    }

    public void paginate() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        // Populate this.count

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Order> root = countCriteria.from(Order.class);
        countCriteria = countCriteria.select(builder.count(root)).where(
                getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria)
                .getSingleResult();

        // Populate this.pageItems

        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        root = criteria.from(Order.class);
        TypedQuery<Order> query = this.entityManager.createQuery(criteria
                .select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(
                getPageSize());
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Order> root) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String name = this.shoppingCart.getOrder().getName();
        if (name != null && !"".equals(name)) {
            predicatesList.add(builder.like(
                    builder.lower(root.<String> get("name")),
                    '%' + name.toLowerCase() + '%'));
        }
        String address = this.shoppingCart.getOrder().getAddress();
        if (address != null && !"".equals(address)) {
            predicatesList.add(builder.like(
                    builder.lower(root.<String> get("address")),
                    '%' + address.toLowerCase() + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    public List<Order> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

	/*
	 * Support listing and POSTing back Order entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

    public List<Order> getAll() {

        CriteriaQuery<Order> criteria = this.entityManager.getCriteriaBuilder()
                .createQuery(Order.class);
        return this.entityManager.createQuery(
                criteria.select(criteria.from(Order.class))).getResultList();
    }



    public Converter getConverter() {

        final OrderBean ejbProxy = this.sessionContext
                .getBusinessObject(OrderBean.class);

        return new Converter() {

            @Override
            public Object getAsObject(FacesContext context,
                                      UIComponent component, String value) {

                return ejbProxy.findById(Long.valueOf(value));
            }

            @Override
            public String getAsString(FacesContext context,
                                      UIComponent component, Object value) {

                if (value == null) {
                    return "";
                }

                return String.valueOf(((Order) value).getId());
            }
        };
    }
}