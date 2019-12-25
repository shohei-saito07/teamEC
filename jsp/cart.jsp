<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags" %>
<!DOCTYPE>
<html>
<head>
	<meta charset=UTF-8>
	<link rel="stylesheet" href="./css/style.css">
	<link rel="stylesheet" href="./css/table.css">

	<script type="text/javascript">
		function checkValue(){
			var checkList = document.getElementsByClassName("checkList");
			var checkFlg = 0;
			for (var i = 0;  i<checkList.length;  i++) {
				if(checkList[i].checked) {
					checkFlg = 1;
					break;
				}
			}
			if (checkFlg == 1) {
		    	document.getElementById('deleteButton').disabled="";
			} else {
				document.getElementById('deleteButton').disabled="true";
			}
		}
	</script>
	<title>カート画面</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<script src="./js/setAction.js"></script>

	<div id = "main">
		<h1>カート画面</h1>
		<div>
			<s:if test="!cartInfoDTOList.size()>0" >
				<div class="messageResult">カート情報がありません。</div>
			</s:if>
			<s:if test="cartInfoDTOList.size()>0">
					<s:form action="DeleteCartAction">
					<table class="horizontal-list-table">
						<thead>
							<tr>
								<th><s:label value="#"/></th>
								<th><s:label value="商品名"/></th>
								<th><s:label value="商品名ふりがな"/></th>
								<th><s:label value="商品画像"/></th>
								<th><s:label value="値段"/></th>
								<th><s:label value="発売会社名"/></th>
								<th><s:label value="発売年月日"/></th>
								<th><s:label value="購入個数"/></th>
								<th><s:label value="合計金額"/></th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="cartInfoDTOList">
								<tr>
									<td><input type="checkbox" name="checkList"  class="checkList" value='<s:property value="productId" />' onchange="checkValue()" ></td>
									<td><s:property value="productName"/></td>
									<td><s:property value="productNameKana"/></td>
									<td><img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>' width="50px" height="50px" /></td>
									<td><s:property value="price"/>円</td>
									<td><s:property value="releaseCompany"/></td>
									<td><s:property value="releaseDate"/></td>
									<td><s:property value="productCount"/></td>
									<td><s:property value="totalPrice"/>円</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<h2 class="total_price"><s:label value="カート合計金額 :"/><s:property value="totalPrice"/>円</h2><br>
					<div class="submit_btn_box_cart">
						<s:submit value="削除" id="deleteButton" class="submit_btn" disabled="true"/>
					</div>
				</s:form>
			</s:if>
			<s:if test="cartInfoDTOList.size()>0">
				<s:form id="form">
<!-- 					<div class="submit_btn_box"> -->
						<s:if test="#session.loginFlg==1">
							<s:submit value="決済" class="submit_btn" onclick="setAction('SettlementConfirmAction')"/>
						</s:if>
						<s:else>
							<s:submit value="決済" class="submit_btn" onclick="setAction('GoLoginAction')"/>
							<s:hidden name="cartFlg"  value="1"/>
						</s:else>
<!-- 					</div> -->
				</s:form>
			</s:if>
		</div>
	</div>
</body>
</html>
