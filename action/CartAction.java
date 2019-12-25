package com.internousdev.django.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.django.dao.CartInfoDAO;
import com.internousdev.django.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport implements SessionAware {

	private Map<String, Object> session;
	private List<CartInfoDTO> cartInfoDTOList = new ArrayList<CartInfoDTO>();
	private int totalPrice;
	private String errorMessage;

	public String execute() throws SQLException {
		
		if (!session.containsKey("userId") && !session.containsKey("kariUserId")) {
			return "sessionTimeout";
		}

		String userId = null;
		CartInfoDAO cartInfoDAO = new CartInfoDAO();

		if (String.valueOf(session.get("loginFlg")).equals("1")) {
			userId = session.get("userId").toString();
		} else {
			userId = session.get("kariUserId").toString();
		}

		cartInfoDTOList = cartInfoDAO.getCartList(userId);
		totalPrice = cartInfoDAO.getTotalPrice(userId);
		return SUCCESS;
	}

	public List<CartInfoDTO> getCartInfoDTOList() {
		return cartInfoDTOList;
	}

	public void setCartInfoDTOList(List<CartInfoDTO> cartInfoDTOList) {
		this.cartInfoDTOList = cartInfoDTOList;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}