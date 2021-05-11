package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.domain.dto.OrderDTO;
import com.example.wechatselldemo.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : guoweifeng
 * @date : 2021/5/10
 */
@Controller
@RequestMapping("/sell/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<Page<OrderDTO>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             Model model) {
        Page<OrderDTO> list = orderMasterService.findList(PageRequest.of(page, size));
//        model.addAttribute("orderDTOPage", list);
//        return new ModelAndView("order/list", "orderListModel", model);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/cancel")
    @ResponseBody
    public ResponseEntity<String> cancel(@RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderMasterService.cancel(orderDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/detail/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderDTO> queryByOrderId(@PathVariable("orderId") String orderId) {
        return new ResponseEntity<>(orderMasterService.findOne(orderId), HttpStatus.OK);
    }
}
