package com.readlearncode.model;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
public class OrderLine implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private Integer quantity;

	@OneToOne(cascade = CascadeType.ALL)
	private Book book;

	public OrderLine(Book book, Integer quantity) {
		this.book = book;
		this.quantity = quantity;
	}

    public OrderLine() {

    }

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity < 0 ? 0 : quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void newBook() {
		this.book = new Book();
	}

	public void addQuantity(int i) {
		quantity++;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (quantity != null)
			result += "quantity: " + quantity;
		return result;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OrderLine)) {
			return false;
		}
		OrderLine other = (OrderLine) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}
}