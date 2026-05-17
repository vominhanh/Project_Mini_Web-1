package com.training.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.training.config.DatabaseUtil;
import com.training.entity.AppUserEntity;

public class UserRepository {

    public AppUserEntity findByUsername(
            String username
    ) {

        try {

            Connection connection =

                    DatabaseUtil
                    .getConnection();

            PreparedStatement ps =

                    connection.prepareStatement(

                            """
                            SELECT *
                            FROM users
                            WHERE username = ?
                            """
                    );

            ps.setString(
                    1,
                    username.trim()
            );

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                AppUserEntity user =
                        new AppUserEntity();

                user.setId(
                        rs.getLong("id")
                );

                user.setUsername(
                        rs.getString("username")
                );

                user.setPassword(
                        rs.getString("password")
                );

                user.setRole(
                        rs.getString("user_role")
                );

                return user;
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}