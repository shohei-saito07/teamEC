package com.internousdev.django.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.django.dto.CartInfoDTO;
import com.internousdev.django.util.DBConnector;

public class CartInfoDAO {

	//カート情報取得機能
	public List<CartInfoDTO> getCartList(String userId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<CartInfoDTO> cartInfoDTOList = new ArrayList<CartInfoDTO>();

		String sql = " SELECT *, (pi.price*ci.product_count) as totalPrice"
				+" FROM product_info AS pi RIGHT JOIN cart_info AS ci ON"
				+" pi.product_id = ci.product_id WHERE user_id = ?"
				//ORDER BYを使い、update_date,regist_dateの順番で降順に並べる
				+" order by ci.update_date desc, ci.regist_date desc";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CartInfoDTO dto = new CartInfoDTO();
				dto.setUserId(rs.getString("ci.user_id"));
				dto.setProductId(rs.getInt("ci.product_id"));
				dto.setProductCount(rs.getInt("ci.product_count"));
				dto.setPrice(rs.getInt("pi.price"));
				dto.setProductName(rs.getString("pi.product_name"));
				dto.setProductNameKana(rs.getString("pi.product_name_kana"));
				dto.setImageFilePath(rs.getString("pi.image_file_path"));
				dto.setImageFileName(rs.getString("pi.image_file_name"));
				dto.setReleaseDate(rs.getDate("pi.release_date"));
				dto.setReleaseCompany(rs.getString("pi.release_company"));
				dto.setTotalPrice(rs.getInt("totalprice"));
				cartInfoDTOList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cartInfoDTOList;
	}

	//カート商品の合計金額を計算する
	public int getTotalPrice(String userId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
        int totalPrice = 0;

		String sql = "SELECT  sum(pi.price * ci.product_count) AS total_price FROM product_info AS pi"
				+ " RIGHT OUTER JOIN cart_info AS ci"
				+ " ON pi.product_id = ci.product_id WHERE user_id = ? GROUP BY user_id";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				totalPrice = rs.getInt("total_price");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return totalPrice;
	}

	//商品の新規追加
	public int addProduct(String userId, int productId, int productCount) {
        
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;

		String sql = "INSERT INTO cart_info (user_id, product_id, product_count, regist_date, update_date)"
				+ " VALUE (?,?,?,now(),now())";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			ps.setInt(3, productCount);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//商品の削除機能
	public int deleteProduct(String userId, String productId) {
        
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;

		String sql = "DELETE FROM cart_info WHERE user_id = ? and product_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, productId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//同一商品の商品個数更新
	public int updateProduct(String userId, int productId, int productCount) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;

		//userIdとproductIdが一致する商品を検索し、商品個数を合計する
		String sql = "UPDATE cart_info SET product_count = (product_count+?), update_date = now()"
				+ " WHERE user_id = ? AND product_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productCount);
			ps.setString(2, userId);
			ps.setInt(3, productId);
			result = ps.executeUpdate();            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//ユーザーIDと商品IDの一致するものを検索
	public boolean checkProduct(String userId, int productId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		boolean result = false;

		String sql = "SELECT * FROM cart_info WHERE user_id = ? AND product_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, productId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (userId.equals(rs.getString("user_id"))) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//新規カート作成
	public int createCart(String userId, String kariUserId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;

		String sql = "UPDATE cart_info SET user_id = ?, update_date = now() where user_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, kariUserId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//カート情報を紐付け
	public int joinCart(String userId, String kariUserId, int productId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result = 0;

		String sql = "UPDATE cart_info SET user_id = ?, update_date = now() where user_id = ? AND product_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, kariUserId);
			ps.setInt(3, productId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	//カートの中身をすべて削除する機能
	public int deleteAll(String UserId) {
        
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		int result= 0;

		String sql = "DELETE FROM cart_info WHERE user_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, UserId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
