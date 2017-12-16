import java.util.StringTokenizer;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import acm.program.*;

public class Gap extends ConsoleProgram {
	
	public void run() {
		String text = readLine("Enter Text :");
		StringTokenizer st = new StringTokenizer(text);
		int a = 0 ;
		while(st.hasMoreTokens()){
			st.nextToken();
			a++;
		}
		println(a);
	}
}
