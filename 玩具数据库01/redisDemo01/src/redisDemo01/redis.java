package redisDemo01;

import java.io.*;
import java.util.*;

public class redis {
	Scanner in = new Scanner(System.in);
	private Map<String, String>[] r = new Map[100];// ��ഴ��100����
	private String[] tableName = new String[100];
	private int num = 0;// ������
	private int target;// ��ǰ�����ı�
	// ������
	public void create(String name) {
		r[num] = new HashMap<String, String>();
		tableName[num] = name;
		target = num;
		num++;
	}
	
	// ������
	public void use(int target) {
		this.target = target;
	}

	public void use(String name) {
		for (int i = 0; i < num; i++) {
			if (tableName[i].equals(name)) {
				target = i;
				break;
			}
		}
	}

	// �ж��Ƿ����
	public boolean contains(String key) {
		return r[target].containsKey(key);
	}

	// ��
	public void add(String key, String value) {
		r[target].put(key, value);
	}

	// ɾ
	public void delete(String key) {
		r[target].remove(key);
	}

	// ��
	public void change(String key, String value) {
		r[target].put(key, value);
	}

	// ��
	public String get(String key) {
		return r[target].get(key);
	}

	// ��ӡ��
	public String show() {
		String ans = "";
		for (String key : r[target].keySet()) {
			ans = ans + key + " " + r[target].get(key) + " ";
		}
		return ans;
	}
	public String showAllTableName() {
		String anString="";
		for(int i=0;i<num;i++)
			anString=anString+i+":"+tableName[i];
		return anString;
	}
	//д��Ӳ��
	public void write() {
		try {
			Writer writer = new FileWriter(new File("member.txt"));
			int i=0;//����±�
			while (i<num) {
				writer.write("table " + tableName[i] + "\r\n");//����
				for (String key : r[i].keySet()) {
					writer.write(key + " " + r[i].get(key) + "\r\n");//����
				}
				i++;
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//������
	public void read() {
		try {

			FileReader fileReader = new FileReader("member.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String str;

			while ((str = bufferedReader.readLine()) != null) {
				String[] a = str.split(" ");
				if (a[0].equals("table")) {
					create(a[1]);//�±�
				} else {
					add(a[0], a[1]);//���������
				}
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��������
	public String command(String aString) {
		String ans = "success";
		String[] cmd = aString.split(" ");

		for (int i = 0; i < cmd.length; i++) {
			cmd[i] = cmd[i].replaceAll(" ", "");
		}

		String command = cmd[0];
		if (command.equals("create")) {
			create(cmd[1]);
		} else if (command.equals("use")) {
			use(cmd[1]);
		} else if (command.equals("add")) {
			add(cmd[1], cmd[2]);
		} else if (command.equals("contains")) {
			if (contains(cmd[1]))
				ans = "This key exists";
			else
				ans = "This key does not exists";
		} else if (command.equals("get")) {
			ans = "key:" + cmd[1] + "  value:" + get(cmd[1]);
		} else if (command.equals("delete")) {
			delete(cmd[1]);
		} else if (command.equals("show")) {
			ans = show();
		} else if (command.equals("showall")) {
			ans = showAllTableName();
		} else if (command.equals("end")) {
			write();
			return "end";
		}else {
			return "false,tryAgain";
		}
		return ans;
	}
}
