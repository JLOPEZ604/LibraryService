import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 * This class handles the CRUD operations of the member table
 * It establishes a connection with the database then generates the table
 *
 */

public class MemberService {

    private final Connection serviceConnection;
    PreparedStatement insertStatement;

    public MemberService(Connection connection) throws SQLException {
        this.serviceConnection = connection;
        Statement statement = serviceConnection.createStatement();
        String executeString = "CREATE TABLE Member (uuid varchar(255), First_Name varchar(255), Last_Name varchar(255), "
                + "Address varchar(255), Phone_Number varchar(255), primary key(uuid) )";
        statement.executeUpdate(executeString);
        insertStatement = serviceConnection.prepareStatement("insert into Member (uuid, First_name, Last_Name, Address, Phone_Number) values(?,?,?,?,?)");
    }

    public Member getById(final String id) throws SQLException {
        PreparedStatement pstmnt = serviceConnection.prepareStatement("select * from Member where uuid = ?");
        pstmnt.setString(1,id);

        ResultSet rset = pstmnt.executeQuery();

        if (!rset.next()) {
            return null;
        }
        return new Member(rset.getString(1),rset.getString(2),rset.getString(3), rset.getString(4), rset.getString(5));

    }

    public Member getByPhoneNumber(final String phoneNumber) throws SQLException {
        PreparedStatement pstmnt = serviceConnection.prepareStatement("select * from Member where Phone_number = ?");
        pstmnt.setString(1,phoneNumber);

        ResultSet rset = pstmnt.executeQuery();
        if (!rset.next()) {
            return null;
        }

        return new Member(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5));
    }

    public Collection<Member> getByLastName(final String lastName) throws SQLException {
        Collection<Member> memberList = new ArrayList<>();
        PreparedStatement pstmnt = serviceConnection.prepareStatement("select * from Member where last_name = ?");
        pstmnt.setString(1, lastName);

        ResultSet rset = pstmnt.executeQuery();
        if (rset.next()) {
            do {
                memberList.add(new Member(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5)));
            } while (rset.next());
            return memberList;
        }

        return null;
    }

    public void createMember(Member member) throws SQLException { // create member based on ID of member
        insertStatement.setString(1, member.getUuid());
        insertStatement.setString(2, member.getFirstName());
        insertStatement.setString(3, member.getLastName());
        insertStatement.setString(4, member.getStreetAddress());
        insertStatement.setString(5, member.getPhoneNumber());

        insertStatement.executeUpdate();

    }

    public void updateMember(Member member) throws SQLException { // update member based on ID of member
        PreparedStatement updateStatement = serviceConnection.prepareStatement("Update Member set First_Name = ?, Last_Name = ?, Address = ?, Phone_Number = ? where uuid = ?");
        updateStatement.setString(1,member.getFirstName());
        updateStatement.setString(2,member.getLastName());
        updateStatement.setString(3, member.getStreetAddress());
        updateStatement.setString(4, member.getPhoneNumber());
        updateStatement.setString(5,member.getUuid());

        updateStatement.executeUpdate();

    }

    public void deleteMember(Member member) throws SQLException { // delete member based on ID of member
        PreparedStatement statement = serviceConnection.prepareStatement("delete from member where uuid = ?");
        statement.setString(1,member.getUuid());
        statement.executeUpdate();
    }
}
