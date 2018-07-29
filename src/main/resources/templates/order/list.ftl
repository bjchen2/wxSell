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
                            <div class="col-md-12 column">
                            <#--商品概要表-->
                                <table class="table table-bordered table-condensed">
                                    <thead>
                                    <tr>
                                        <th>订单id</th>
                                        <th>姓名</th>
                                        <th>手机号</th>
                                        <th>地址</th>
                                        <th>金额</th>
                                        <th>订单状态</th>
                                        <th>支付方式</th>
                                        <th>支付状态</th>
                                        <th>创建时间</th>
                                        <th colspan="2">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                            <#list orderDtoPage.getContent() as orderDto>
                            <tr>
                                <td>${orderDto.orderId}</td>
                                <td>${orderDto.userName}</td>
                                <td>${orderDto.userPhone}</td>
                                <td>${orderDto.userAddress}</td>
                                <td>${orderDto.orderAmount}</td>
                                <td>${orderDto.getOrderStatusEnum().message}</td>
                                <td>微信</td>
                                <td>${orderDto.getPayStatusEnum().message}</td>
                                <td>${orderDto.createTime}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDto.orderId}">详情</a></td>
                                <td>
                                <#--当订单为新下单状态时才显示取消操作-->
                                    <#if orderDto.orderStatus == 0>
                                        <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}">取消</a>
                                    </#if>
                                </td>
                            </tr>
                            </#list>
                                    </tbody>
                                </table>
                            <#--分页-->
                                <ul class="pagination">
                                <#--lte表示小于等于，gte表示大于等于-->
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                            </#if>
                                <#--0..orderDtoPage.getTotalPages()表示一个0到orderDtoPage.getTotalPages()的list-->
                            <#list 1..orderDtoPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                                <#--lte表示小于等于，gte表示大于等于-->
                            <#if currentPage gte orderDtoPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                            </#if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </body>
</html>