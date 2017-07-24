public class Substring{

public static CopyBean genSubstring(String in) { 

CopyBean c = new CopyBean(); 

  c.setEqpinit( in.substring(0,4));
  c.setEqpnumbspace( in.substring(4,8));
  c.setEqpnumbA( in.substring(8,11));
  c.setEqpnumbB( in.substring(11,14));
  c.setCarkind( in.substring(14,18));

return c;
	}
}