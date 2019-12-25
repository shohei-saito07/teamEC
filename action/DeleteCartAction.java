package com.internousdev.django.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.django.dao.CartInfoDAO;
import com.internousdev.django.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCartAction extends ActionSupport implements SessionAware {

	private List<CartInfoDTO> cartInfoDTOList = new ArrayList<CartInfoDTO>();
	private Map<String, Object> session;
	private int totalPrice;
	private String[] checkList;

	public String execute() throws SQLException {

		if (!session.containsKey("userId") && !session.containsKey("kariUserId")) {
			return "sessionTimeout";
		}

		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		String userId = null;
		int count = 0;

		if ((session.get("loginFlg").toString()).equals("1")) {
			userId = session.get("userId").toString();
		} else {
		 	userId = session.get("kariUserId").toString();
		}

		for (String productId : checkList) {
			count += cartInfoDAO.deleteProduct(userId, productId);
		}

		if (count == 0) {
			return ERROR;
		}

		setCartInfoDTOList(cartInfoDAO.getCartList(userId));
		totalPrice = cartInfoDAO.getTotalPrice(userId);
		return SUCCESS;
	}

	public Map<String,Object> getSession() {
	    return session;
	}

	public void setSession(Map<String,Object> session) {
	    this.session = session;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String[] getCheckList() {
	    return checkList;
	}

	public void setCheckList(String[] checkList) {
	    this.checkList = checkList;
	}

	public List<CartInfoDTO> getCartInfoDTOList() {
		return cartInfoDTOList;
	}

	public void setCartInfoDTOList(List<CartInfoDTO> cartInfoDTOList) {
		this.cartInfoDTOList = cartInfoDTOList;
	}
}