package br.com.alura.ecommerce;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        orderDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var email = req.getParameter("email");

            var orderId = req.getParameter("uuid");
            var amount = new BigDecimal(req.getParameter("amount"));

            var order = new Order(orderId, amount, email);

            try(var database = new OrdersDatabase()) {
                if (database.wasProcessed(order)) {
                    System.out.println("Old order received!");

                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Old order received!");
                } else {
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, new CorrelationId(NewOrderServlet.class.getSimpleName()), order);

                    System.out.println("New order sent successfully!");

                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("New order sent successfully!");
                }
            }
        } catch (ExecutionException e) {
            throw new ServletException(e);
        } catch (InterruptedException e) {
            throw new ServletException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
