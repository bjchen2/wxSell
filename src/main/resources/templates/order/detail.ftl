<html>
<#include "../common/header.ftl">
<body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">
         <#--主要内容-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <div class="row clearfix">
                            <div class="col-md-5 column">
                                <table class="table table-bordered table-condensed">
                                    <thead>
                                    <tr>
                                        <th>订单id</th>
                                        <th>订单总金额</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>${orderDto.orderId}</td>
                                        <td>${orderDto.orderAmount}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        <#--商品详情表格，包含该订单各个商品的价格-->
                            <div class="col-md-12 column">
                                <table class="table table-bordered table-condensed">
                                    <thead>
                                    <tr>
                                        <th>商品id</th>
                                        <th>商品名称</th>
                                        <th>商品单价</th>
                                        <th>商品数量</th>
                                        <th>合计</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                            <#list orderDto.orderDetailList as orderDetail>
                            <tr>
                                <td>${orderDetail.productId}</td>
                                <td>${orderDetail.productName}</td>
                                <td>${orderDetail.productPrice}</td>
                                <td>${orderDetail.productQuantity}</td>
                                <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                            </tr>
                            </#list>
                                    </tbody>
                                </table>
                            </div>
                        <#--仅新下单状态显示这两个按钮-->
                    <#if orderDto.orderStatus == 0>
                        <div class="col-md-12 column">
                            <a href="/sell/seller/order/finish?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                            <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                        </div>
                    </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>