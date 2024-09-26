package vo;

import java.sql.Date;

public class MaterialVO {
    private String t_corr;
    private String s_incom;
    private Date s_date;
    private int t_value;
    private String p_no;
    private int m_no; // int형
    private String m_dept;
    private int s_amount;
    private int w_no; // int형

    // 생성자
    public MaterialVO() {}

    public MaterialVO(String t_corr, String s_incom, Date s_date, int t_value,
                      String p_no, int m_no, String m_dept, int s_amount, int w_no) {
        this.t_corr = t_corr;
        this.s_incom = s_incom;
        this.s_date = s_date;
        this.t_value = t_value;
        this.p_no = p_no;
        this.m_no = m_no;
        this.m_dept = m_dept;
        this.s_amount = s_amount;
        this.w_no = w_no;
    }

    // Getter 및 Setter
    public String getT_corr() { return t_corr; }
    public void setT_corr(String t_corr) { this.t_corr = t_corr; }
    public String getS_incom() { return s_incom; }
    public void setS_incom(String s_incom) { this.s_incom = s_incom; }
    public Date getS_date() { return s_date; }
    public void setS_date(Date s_date) { this.s_date = s_date; }
    public int getT_value() { return t_value; }
    public void setT_value(int t_value) { this.t_value = t_value; }
    public String getP_no() { return p_no; }
    public void setP_no(String p_no) { this.p_no = p_no; }
    public int getM_no() { return m_no; }
    public void setM_no(int m_no) { this.m_no = m_no; }
    public String getM_dept() { return m_dept; }
    public void setM_dept(String m_dept) { this.m_dept = m_dept; }
    public int getS_amount() { return s_amount; }
    public void setS_amount(int s_amount) { this.s_amount = s_amount; }
    public int getW_no() { return w_no; }
    public void setW_no(int w_no) { this.w_no = w_no; }
}
