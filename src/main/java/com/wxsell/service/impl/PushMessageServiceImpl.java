package com.wxsell.service.impl;

import com.wxsell.config.WechatAccountConfig;
import com.wxsell.constant.PushMessageConstant;
import com.wxsell.dto.OrderDto;
import com.wxsell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By Cx On 2018/7/30 22:42
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    WxMpService wxMpService;
    @Autowired
    WechatAccountConfig wechatAccountConfig;

    @Override
    public void orderFinish(OrderDto orderDto) {
        WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //TODO 因为支付功能无法运行，所以暂时模拟发送给自己
        //wxMpTemplateMessage.setToUser(orderDto.getUserOpenid());
        wxMpTemplateMessage.setToUser("oZ0Q608VVI-SEewLJSnA2cb5sh3A");
        wxMpTemplateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
        //添加推送消息数据
        wxMpTemplateMessage.addData(new WxMpTemplateData("first", PushMessageConstant.ORDER_FINISHED));
        wxMpTemplateMessage.addData(new WxMpTemplateData("keynote1", PushMessageConstant.SELLER_NAME));
        wxMpTemplateMessage.addData(new WxMpTemplateData("keynote2", "123455678"));
        wxMpTemplateMessage.addData(new WxMpTemplateData("keynote3", orderDto.getOrderId()));
        wxMpTemplateMessage.addData(new WxMpTemplateData("keynote4", orderDto.getOrderStatusEnum().getMessage()));
        wxMpTemplateMessage.addData(new WxMpTemplateData("keynote5", "￥"+orderDto.getOrderAmount()));
        wxMpTemplateMessage.addData(new WxMpTemplateData("remark", PushMessageConstant.END_MESSAGE));
        //异常用try-catch捕获，防止抛出异常造成回滚
        try {
            templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException e){
            log.error("[微信消息推送]推送失败，e={}",e);
        }
    }
}
