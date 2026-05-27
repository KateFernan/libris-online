package application;

import java.sql.*;
import database.DBConnection;

public class UserDAO {

    public static void enableTwoFactor(String username) {
        try (Connection conn = DBConnection.connect()) {

            String sql =
                "UPDATE public.users SET two_factor_enabled = true WHERE username = ?";

            PreparedStatement stmt =
                conn.prepareStatement(sql);

            stmt.setString(1, username);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean banUser(String username) {
        try(Connection conn = DBConnection.connect()) {

            String sql = """
                UPDATE public.users
                SET account_status = 'BANNED'
                WHERE username = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean unbanUser(String username) {
        try(Connection conn = DBConnection.connect()) {

            String sql = """
                UPDATE public.users
                SET account_status = 'ACTIVE'
                WHERE username = ?
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean updateUsername(
            String oldUsername,
            String newUsername
    ) {
        String sql = """
            UPDATE public.users
            SET username = ?
            WHERE username = ?
        """;

        try(
            Connection conn = DBConnection.connect();
            PreparedStatement stmt =
                conn.prepareStatement(sql)
        ){
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);

            return stmt.executeUpdate() > 0;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean deleteUser(
            String username
    ) {
        String sql = """
            DELETE FROM public.users
            WHERE username = ?
        """;

        try(
            Connection conn = DBConnection.connect();
            PreparedStatement stmt =
                conn.prepareStatement(sql)
        ){
            stmt.setString(1, username);

            return stmt.executeUpdate() > 0;

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean isUserBanned(String username) {
        try(Connection conn = DBConnection.connect()) {

            String sql = """
                SELECT account_status
                FROM public.users
                WHERE username = ?
            """;

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                String status =
                        rs.getString("account_status");

                return status.equalsIgnoreCase("BANNED");
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public static User findUserByUsername(
            String username
    ) {

        try(Connection conn = DBConnection.connect()) {

            String sql = """
                SELECT *
                FROM public.users
                WHERE LOWER(username)=LOWER(?)
            """;

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {

            	return new User(
            	        rs.getInt("user_id"),
            	        rs.getString("username"),
            	        rs.getString("password"),
            	        rs.getString("email"),
            	        Role.valueOf(
            	        	    rs.getString("role")
            	        	      .trim()
            	        	      .toUpperCase()
            	        	),
            	        rs.getBoolean("is_verified"),
            	        rs.getBoolean("is_locked"),
            	        rs.getInt("failed_login_attempts"),
            	        rs.getBoolean("is_premium")
            	);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    } 
}