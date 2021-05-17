package end.bean;


public class Orderitem {

  int id;   //订单项号
  String oid;  //属于哪个订单，关联oder表的主键id
  int pid;  //商品id，关联product表主键id
  float price;  //商品单价，订购时候确定，不再随着商品调价而浮动
  int num;  //此商品订购数
  String  proName;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public String getProName() {
    return proName;
  }

  public void setProName(String proName) {
    this.proName = proName;
  }
}
