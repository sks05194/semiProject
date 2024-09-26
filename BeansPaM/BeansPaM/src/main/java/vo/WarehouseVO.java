package vo;

public class WarehouseVO {
    private int w_no;
    private String w_loc;
    private double w_temp;
    private double w_humi;
    private int m_no;
    private String w_issue;
 

    // Getters and Setters
    public int getW_no() { return w_no; }
    public void setW_no(int w_no) { this.w_no = w_no; }

    public String getW_loc() { return w_loc; }
    public void setW_loc(String w_loc) { this.w_loc = w_loc; }

    public double getW_temp() { return w_temp; }
    public void setW_temp(double w_temp) { this.w_temp = w_temp; }

    public double getW_humi() { return w_humi; }
    public void setW_humi(double w_humi) { this.w_humi = w_humi; }

    public int getM_no() { return m_no; }
    public void setM_no(int m_no) { this.m_no = m_no; }

   public String getW_issue() { return w_issue; }
   public void setW_issue(String w_issue) { this.w_issue = w_issue; }
}