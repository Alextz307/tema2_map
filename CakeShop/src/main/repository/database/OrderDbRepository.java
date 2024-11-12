package main.repository.database;

import main.domain.Order;
import main.repository.IRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDbRepository implements IRepository<Long, Order<Long>> {
    private final String url;

    public OrderDbRepository(String url) {
        this.url = url;
    }

    @Override
    public Long add(Order<Long> order) {
        String sql = "INSERT INTO Orders (cakeId, customerName, quantity, status, createdAt) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, order.getCakeId());
            pstmt.setString(2, order.getCustomerName());
            pstmt.setInt(3, order.getQuantity());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                order.setId(id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Order<Long>> findAll() {
        List<Order<Long>> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order<Long> order = new Order<>(
                        rs.getLong("cakeId"),
                        rs.getString("customerName"),
                        rs.getInt("quantity")
                );
                order.setId(rs.getLong("id"));
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
    public Optional<Order<Long>> findById(Long id) {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Order<Long> order = new Order<>(
                        rs.getLong("cakeId"),
                        rs.getString("customerName"),
                        rs.getInt("quantity")
                );
                order.setId(rs.getLong("id"));
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
    public void modify(Order<Long> updatedOrder) {
        String sql = "UPDATE Orders SET cakeId = ?, customerName = ?, quantity = ?, status = ?, createdAt = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, updatedOrder.getCakeId());
            pstmt.setString(2, updatedOrder.getCustomerName());
            pstmt.setInt(3, updatedOrder.getQuantity());
            pstmt.setString(4, updatedOrder.getStatus());
            pstmt.setString(5, updatedOrder.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setLong(6, updatedOrder.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Orders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
