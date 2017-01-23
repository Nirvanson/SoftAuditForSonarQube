package vinothek.controller;

import java.util.List;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vinothek.model.AngestelltenRepository;
import vinothek.model.Angestellter;
import vinothek.model.KundenRepository;
import vinothek.model.RepeatOrder;
import vinothek.model.RepeatOrderRepository;


@Controller
class BilanzController {
	private final AngestelltenRepository angestelltenRepository;
	private final OrderManager<Order> orderManager;
	private final RepeatOrderRepository rOrderRepo;
	
	
	
	@Autowired
	public BilanzController(KundenRepository kundenRepository, AngestelltenRepository angestelltenRepository,
			UserAccountManager userAccountManager, OrderManager<Order> orderManager, RepeatOrderRepository rOrderRepo) {


		this.angestelltenRepository = angestelltenRepository;
		this.orderManager = orderManager;
		this.rOrderRepo = rOrderRepo;
		
	}
	
	/**
	 * Methode zum Anzeigen der Bilanz (Einnahmen dur Bestellungen - Gehälter der Angestellten - Ausgaben durch Nachbestellungen), 
	 * in tabellarischer Form und durch Diagramme
	 * 
	 * @param model
	 */
	@PreAuthorize("hasRole('ROLE_BOSS')")
	@RequestMapping("/bilanz")
		public void createBilanz(Model model){
		model.addAttribute("order", orderManager.findBy(OrderStatus.COMPLETED));
		model.addAttribute("angestelltenListe", angestelltenRepository.findAll());
		model.addAttribute("rorder", rOrderRepo.findAll());
		//System.out.println(" alle Einkäufe :" + rOrderRepo.findAll());
		
		List<Angestellter> angestelltenListe = angestelltenRepository.findAll();
		System.out.println(orderManager.findBy(OrderStatus.COMPLETED));
		
		
		
		Money ogp =Money.of(0, "EUR");
		for(Order s:orderManager.findBy(OrderStatus.COMPLETED)){
			//Money amax = amax + s.getTotalPrice();
			//System.out.println(amax);
			ogp =ogp.add(s.getTotalPrice()).with(Monetary.getDefaultRounding());
			
		}
		
		Money rpreis=  Money.of(0, "EUR");
		for(RepeatOrder r:rOrderRepo.findAll())	{
			
			 rpreis =  rpreis.add( r.getFullPrice()).with(Monetary.getDefaultRounding());
			 
			 
		}

		 Money gehaltgp = Money.of(0, "EUR");
		for( Angestellter a:angestelltenRepository.findAll()){
			
			gehaltgp = gehaltgp.add(a.getGehalt()).with(Monetary.getDefaultRounding());
			
			
		}
		
		MonetaryAmount roundedAmount = ogp.with(Monetary.getDefaultRounding());
		System.out.println("Ordergesamt= " + ogp);
		System.out.println("Ordergesamt (ROUNDED)   " + roundedAmount);
		System.out.println("Repeatordergesamt= " + rpreis);
		System.out.println("Gehaltgesamt= " + gehaltgp);
		Money Gesamtpreis = ogp.subtract(gehaltgp.add(rpreis)).with(Monetary.getDefaultRounding());
		 model.addAttribute("orPreis",rpreis);
		 model.addAttribute("roPreis",ogp);
		 model.addAttribute("gePreis",gehaltgp);
		 model.addAttribute("gesamtPreis",Gesamtpreis);	
		
	}
	
}
