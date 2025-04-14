<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.CartProduct" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CartProduct> cartItems = (List<CartProduct>) request.getAttribute("cartItems");
    if (cartItems == null) cartItems = new java.util.ArrayList<>();
    DecimalFormat formatter = new DecimalFormat("#,###");
%>

<span id="countProduct"><%= cartItems.size() %></span>

<div class="list-item">
    <% for (CartProduct c : cartItems) { %>
    <div class="d-flex items-center loc-relative item">
        <div class="product-img">
            <img src="<%=c.getImage()%>" height="120" width="120"/>
        </div>
        <div class="w-full pd-12">
            <h4 class="font-600 text-title"><%=c.getProductName()%></h4>
            <div class="w-full wrapper justify-between" style="margin-top: 8px;">
                <span class="duration-round-border">Thời hạn: <%=c.getDuration()%></span>
                <div class="d-flex btn-add-sub">
                    <a href="#" class="btn-sub-product" data-id="<%=c.getProduct_id()%>">-</a>
                    <div class="count"><span><%=c.getQuantity()%></span></div>
                    <a href="#" class="btn-add-product" data-id="<%=c.getProduct_id()%>">+</a>
                </div>
                <span class="cost checkbox-cost"> <%= formatter.format(c.getPrice()) %>đ</span>
            </div>
            <hr class="w-full" style="margin: 10px 0;">
            <a href="#" class="btn-delete" data-id="<%=c.getProduct_id()%>">
                <div class="f-right"><i class="fas fa-trash-alt"></i></div>
            </a>
        </div>
    </div>
    <% } %>
</div>