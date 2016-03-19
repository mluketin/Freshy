package com.akatsuki.freshy.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akatsuki.freshy.service.actions.ActionBig;
import com.akatsuki.freshy.service.actions.ActionSmall;

public class DbAdapter {
  private final String url = "jdbc:postgresql://localhost:5432/freshy?user=freshy&password=freshy";
  private final String driver = "org.postgresql.Driver";

  private Connection connection;

  public void open() throws ClassNotFoundException, SQLException {
    Class.forName(driver);

    connection = DriverManager.getConnection(url);
    if (connection == null) {
      System.out.println("Connection is null");
    } else {
      System.out.println("Connection is OK");
    }
  }

  public void close() throws SQLException {
    connection.close();
  }

  public void create(ActionBig action) throws SQLException {
    // TODO pretrazivanje postojeceg ID && URL - triba vidit kako ce bit
    // impelementirano s klientom
    // Statement stmt = null;
    // stmt = connection.createStatement();
    // String sql = "SELECT deviceid, url FROM main WHERE deviceid like '" +
    // action.getDeviceId() + "' "
    // + "AND url like '" + action.getUrl() + "';";
    // ResultSet rs = stmt.executeQuery(sql);
    // // STEP 5: Extract data from result set
    // while (rs.next()) {
    // // Retrieve by column name
    // int id = rs.getInt("id");
    // int age = rs.getInt("age");
    // String first = rs.getString("first");
    // String last = rs.getString("last");
    //
    // // Display values
    // System.out.print("ID: " + id);
    // System.out.print(", Age: " + age);
    // System.out.print(", First: " + first);
    // System.out.println(", Last: " + last);
    // }
    // rs.close();

    final String insertSql = "INSERT INTO main (url, deviceid, words, img, video, audio, link, active, sha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
    preparedStatement.setString(1, action.getUrl());
    preparedStatement.setString(2, action.getDeviceId());
    preparedStatement.setString(3, "");
    preparedStatement.setBoolean(4, action.isImage());
    preparedStatement.setBoolean(5, action.isVideo());
    preparedStatement.setBoolean(6, action.isAudio());
    preparedStatement.setBoolean(7, action.isLink());
    preparedStatement.setBoolean(8, true);
    preparedStatement.setString(9, null);
    preparedStatement.executeUpdate();
    preparedStatement.close();
  }


  //TODO ovo sa rijecima
  public void update(ActionBig action) throws SQLException {
    final String updateSql = "UPDATE main SET img=" + action.isImage() + ", " + "video=" + action.isVideo() + ", " + "audio="
        + action.isAudio() + ", " + "link=" + action.isLink() + " WHERE " + "url like '" + action.getUrl()
        + "' AND deviceid like '" + action.getDeviceId() + "';";

    Statement stmt = null;
    stmt = connection.createStatement();
    stmt.executeUpdate(updateSql);
  }

  public void pause(ActionSmall action) throws SQLException {
    final String updateSql = "UPDATE main SET active=false WHERE url like '" + action.getUrl()
        + "' AND deviceid like '" + action.getDeviceId() + "';";

    Statement stmt = null;
    stmt = connection.createStatement();
    stmt.executeUpdate(updateSql);
  }

  public void resume(ActionSmall action) throws SQLException {
    final String updateSql = "UPDATE main SET active=true WHERE url like '" + action.getUrl()
        + "' AND deviceid like '" + action.getDeviceId() + "';";

    Statement stmt = null;
    stmt = connection.createStatement();
    stmt.executeUpdate(updateSql);
  }

  public List<String> getAllActiveUrls() throws SQLException {
    final List<String> listUrls = new ArrayList<>();

    final String selectSql = "SELECT DISTINCT url FROM main WHERE active=true;";
    Statement stmt = null;
    stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(selectSql);

    while (rs.next()) {
      listUrls.add(new String(rs.getString(1)));
    }
    rs.close();
    return listUrls;
  }

  public String getWords(String url) throws SQLException {
    String words = null;
    final String selectSql = "SELECT words FROM main WHERE url like '" + url + "';";

    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(selectSql);

    if(rs.next())
      words = rs.getString(1);
    rs.close();
    return words;
  }
}
