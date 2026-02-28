package shop.member;

import java.util.Date;

public class MemberDTO {

	private String id;
	private String pw;
	private String name;
	private String tel;
	private Date reg_date; // yyyy-mm-dd

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	@Override
	public String toString() {

		String str;
		str = String.format("%s, ***, %s, %s, %s", id, pw, name, tel, reg_date);
		return str;
	}
}