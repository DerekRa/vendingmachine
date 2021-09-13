package com.km.vendingmachine.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url="${item.request.url}", name="vmcustomerordertraffic")
public interface DeleteOrderClient {

	@DeleteMapping(value="${item.request.api}")
	public void deleteCustomerOrder(@PathVariable int id);
}
