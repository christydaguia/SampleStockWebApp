package org.xty.stock.main;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Servlet implementation class ConnectAPI
 */
@WebServlet("/ConnectAPI")
public class ConnectAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public String strResponse;
	public boolean initial = true;
	public int numShares=0;
	public int currentShares = 0;
	public BigDecimal currentBal;
	public BigDecimal price;
	public BigDecimal tranPrice;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectAPI() {
        super();
        // TODO Auto-generated constructor stub        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();	    
	    session.setAttribute("initial", initial);
	    request.setAttribute("message", "New Transaction");
	    
	    if (session.getAttribute("initial").toString() == "true") {	    	
	    	BigDecimal initialBal = new BigDecimal(1000);
	    	currentBal = initialBal;
	    }
		
		Stock stock = YahooFinance.get("INTC");
		String name = stock.getName();
		price = stock.getQuote().getPrice();
		
		request.setAttribute("name", name.toString());
		request.setAttribute("price", price.toString());		
		 
		stock.print();
		
		if (request.getParameter("numShares") != null) {
			numShares = Integer.parseInt(request.getParameter("numShares"));
			tranPrice =  price.multiply(new BigDecimal(numShares));
			request.setAttribute("tranPrice", tranPrice);
			System.out.println(tranPrice);
			if (request.getParameter("btnBuy") != null) {
				request.setAttribute("message", "BuyShare");
	            BuyShare();     
	        } else if (request.getParameter("btnSell") != null) {
	        	request.setAttribute("message", "SellShare");
	            SellShare();
	        } else {        	
	        	request.setAttribute("message", "Select New Transaction");
	        }
		}
		
		session.setAttribute("initial", initial);
		request.setAttribute("currentBalance", currentBal);
		request.setAttribute("currentShares", currentShares);
        
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("/Main.jsp");
        reqDispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);		
	}
	
	public void BuyShare(){
		initial = false;
		currentShares = currentShares + numShares;
		currentBal = currentBal.subtract(tranPrice);
	}
	
	public void SellShare(){
		initial = false;
		currentShares = currentShares - numShares;
		currentBal = currentBal.add(tranPrice);
	}

}
