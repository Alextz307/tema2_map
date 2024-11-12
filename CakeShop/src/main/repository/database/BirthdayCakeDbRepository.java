package main.repository.database;

import main.domain.BirthdayCake;
import main.repository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BirthdayCakeDbRepository implements IRepository<Long, BirthdayCake<Long>> {
    private final String url;

    public BirthdayCakeDbRepository(String url) {
        this.url = url;
    }

    @Override
    public Long add(BirthdayCake<Long> cake) {
        String sql = "INSERT INTO BirthdayCake (name, flavor, price, layers) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cake.getName());
            pstmt.setString(2, cake.getFlavor());
            pstmt.setDouble(3, cake.getPrice());
            pstmt.setInt(4, cake.getLayers());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                cake.setId(id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<BirthdayCake<Long>> findAll() {
        List<BirthdayCake<Long>> cakes = new ArrayList<>();
        String sql = "SELECT * FROM BirthdayCake";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BirthdayCake<Long> cake = new BirthdayCake<>(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("flavor"),
                        rs.getDouble("price"),
                        rs.getInt("layers")
                );
                cakes.add(cake);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cakes;
    }

    @Override
    public Optional<BirthdayCake<Long>> findById(Long id) {
        String sql = "SELECT * FROM BirthdayCake WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BirthdayCake<Long> cake = new BirthdayCake<>(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("flavor"),
                        rs.getDouble("price"),
                        rs.getInt("layers")
                );
                return Optional.of(cake);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void modify(BirthdayCake<Long> updatedCake) {
        String sql = "UPDATE BirthdayCake SET name = ?, flavor = ?, price = ?, layers = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedCake.getName());
            pstmt.setString(2, updatedCake.getFlavor());
            pstmt.setDouble(3, updatedCake.getPrice());
            pstmt.setInt(4, updatedCake.getLayers());
            pstmt.setLong(5, updatedCake.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM BirthdayCake WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
