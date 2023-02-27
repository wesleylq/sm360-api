package com.agency.sm360api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import com.agency.sm360api.domains.dealer.Dealer;
import com.agency.sm360api.domains.dealer.DealerController;
import com.agency.sm360api.domains.dealer.DealerService;
import com.agency.sm360api.domains.listing.ListingController;
import com.agency.sm360api.domains.listing.ListingDto;
import com.agency.sm360api.domains.listing.ListingService;
import com.agency.sm360api.domains.listing.State;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest({ DealerController.class, ListingController.class })
public class ControllersTests {

	@MockBean
	@Autowired
	private DealerService dealerService;

	@MockBean
	@Autowired
	private ListingService listingService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Page<Dealer> dealerPage;
	private List<Dealer> dealers;

	Pageable paging;

	@BeforeEach
	void init() {

		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();

		HttpServletRequest mockRequest = new MockHttpServletRequest();
		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
		RequestContextHolder.setRequestAttributes(servletRequestAttributes);

		Dealer dealer1 = new Dealer(UUID.randomUUID(), "SelectCar", 2);
		Dealer dealer2 = new Dealer(UUID.randomUUID(), "MT Motors", 5);
		Dealer dealer3 = new Dealer(UUID.randomUUID(), "Finesse Auto", 7);

		dealers = new ArrayList<Dealer>(
				Arrays.asList(dealer1, dealer2, dealer3));

		paging = PageRequest.of(0, 10, Sort.by("name"));
		dealerPage = new PageImpl<Dealer>(dealers, paging, 10);
	}

	@AfterEach
	void clear() {
		RequestContextHolder.resetRequestAttributes();
		dealers.clear();
	}

	@Test
	void shouldReturnPagedListOfDealers() throws Exception {

		Mockito.when(dealerService.getDealers(paging)).thenReturn(dealerPage);

		mockMvc.perform(get("/dealers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size", Matchers.is(10)))
				.andExpect(jsonPath("$.content[0].name", Matchers.is("SelectCar")))
				.andExpect(jsonPath("$.content[2].tierLimit", Matchers.is(7)));
	}

	@Test
	void shouldCreateDealer() throws Exception {

		Dealer dealer = new Dealer(UUID.randomUUID(), "Sparke Automotive", 1);

		mockMvc.perform(post("/dealers")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dealer)))
				.andExpect(status().isCreated());
	}

	@Test
	void shouldExceedDealerLimit() throws Exception {

		Dealer dealer = new Dealer(UUID.randomUUID(), "Sparke Automotive", 1);

		ListingDto listing1 = new ListingDto(dealer.getId(), "Audi Q7", 200.0f, State.PUBLISHED);
		ListingDto listing2 = new ListingDto(dealer.getId(), "Ford Explorer", 500.0f, State.PUBLISHED);
		ListingDto listing3 = new ListingDto(dealer.getId(), "Lexus NX", 300.0f, State.DRAFT);

		mockMvc.perform(post("/dealers")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dealer)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/listings")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(listing1)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/listings")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(listing2)).characterEncoding("utf-8"))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/listings")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(listing3)))
				.andExpect(status().isCreated());
	}

}
