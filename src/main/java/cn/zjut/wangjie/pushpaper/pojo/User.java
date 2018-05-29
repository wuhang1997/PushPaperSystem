package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	private Integer uid;
	private String name; 
	private String email;
	private String password;
	private String preferences;

	
	
}
