package main.repository.database;

import main.domain.Order;
import main.repository.IRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDbRepository implements IRepository<Integer, Order<Integer>> {
    private final String url;

    public OrderDbRepository(String url) {
        this.url = url;
    }

    @Override
    public Integer add(Order<Integer> order) {
        String sql = "INSERT INTO Orders (cakeId, customerName, quantity, status, createdAt) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getCakeId());
            pstmt.setString(2, order.getCustomerName());
            pstmt.setInt(3, order.getQuantity());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                Integer id = rs.getInt(1);
                order.setId(id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Order<Integer>> findAll() {
        List<Order<Integer>> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order<Integer> order = new Order<>(
                        rs.getInt("cakeId"),
                        rs.getString("customerName"),
                        rs.getInt("quantity")
                );
                order.setId(rs.getInt("id"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(LocalDateTime.parse(rs.getString("createdAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Optional<Order<Integer>> findById(Integer id) {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Order<Integer> order = new Order<>(
                        rs.getInt("cakeId"),
                        rs.getString("customerName"),
                        rs.getInt("quantity")
                );
                order.setId(rs.getInt("id"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(LocalDateTime.parse(rs.getString("createdAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                return Optional.of(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void modify(Order<Integer> updatedOrder) {
        String sql = "UPDATE Orders SET cakeId = ?, customerName = ?, quantity = ?, status = ?, createdAt = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, updatedOrder.getCakeId());
            pstmt.setString(2, updatedOrder.getCustomerName());
            pstmt.setInt(3, updatedOrder.getQuantity());
            pstmt.setString(4, updatedOrder.getStatus());
            pstmt.setString(5, updatedOrder.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setInt(6, updatedOrder.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Orders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
