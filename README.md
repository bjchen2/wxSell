# wxSell
慕课网Spring Boot企业微信点餐系统<br/><br/>
 
- 慕课网Spring Boot企业微信点餐系统开发源码，大体写法和视频一样，部分地方以个人理解做了适当更改<br/>
 参考资料：<br/>
&emsp;慕课视频https://coding.imooc.com/class/117.html<br/>
&emsp;微信公众平台公众号开发<br/>
&emsp;微信开放平台——用于第三方微信登录<br/>
&emsp;github轮子，用于调用微信公众号接口 https://github.com/Wechat-Group/weixin-java-tools<br/>
&emsp;github轮子，用于调用微信支付接口 https://github.com/Pay-Group/best-pay-sdk<br/>
 
- **代码写于：2018-07-28**<br/>
   &emsp;JDK：JDK1.8版本，<br/>
   &emsp;IDE：IDEA 2017.3.3，<br/>
   &emsp;操作系统为：Windows10<br/>
   &emsp;SpringBoot：2.0.2.RELEASE<br/><br/>
   
- **写在前面**：支付和微信扫码登录功能可能有bug：因为没有合适的商户号可以使用支付和第三方登录接口，所以只是跟着视频操作了一波<br/>
 
- **注意事项**：<br/>

1.数据库设计<br/>
&emsp;一般项目id可以用自增类型的<br/>
&emsp;但一些敏感项目，id可以使用varchar，手动生成ID，避免暴露数据库中的数据数量<br/><br/>
  
2.关于JPA的findOne方法<br/>
&emsp;SB2.0以后不再指出repository.findOne(id),所以要么降低SB版本，要么使用repository.findById(id).get()<br/>
	
3.关于商品单价<br/>
&emsp;I、涉及金额的项目，都应该使用BigDecimal而不使用Double：<br/>
&emsp;&emsp;Double类型的数据使用二进制表示的，它不可能将0.1或者10的其他任何次负幂，精确表示为一个有限长度的二进制小数<br/>
&emsp;&emsp;所以：1.51-1.38出现0.13000000000000012<br/>
&emsp;&emsp;使用BigDecimal类，会自动映射数据库中的Decimal属性<br/>
&emsp;II、关于价格的数据，不能从前端获取（防止被篡改），应该直接从数据库中获取。<br/><br/>
		
4.如果希望Controller返回的Json为自定义名称而不是变量名，可使用@JsonProperty，如下，返回的Json数据为name，而不是categoryName：<br/>
&emsp;&emsp;@JsonProperty("name")<br/>
&emsp;&emsp;private String categoryName;<br/>
	
5.有时domain层实体类的某些信息比较 敏感/私密/无用 ，则不返回给前端，重新定义一个对象（该对象去掉实体类的敏感信息即可）用于响应<br/><br/>

6.如果两个类的很多属性名都相同的话，不用一个个set，可以使用 BeanUtils.copyProperties(source,target);直接拷贝源目标的值给目标对象<br/>
&emsp;如： BeanUtils.copyProperties(pi,productInfoVO);<br/>
&emsp;注意：属性拷贝会覆盖所有公有属性值，所以谨慎使用<br/><br/>

========================以下可观看视频5.4=============================<br/>
访问服务器地址： 如：192.168.123.200（在服务器使用ifconfig查看服务器地址,教程中已经把该ip在host文件中改为sell.com）访问首页，<br/>
若要pc端正常访问可以设置一个openid的cookie，方法如下：<br/>
&emsp;先访问http://192.168.123.200/#/order （因为这个页面不跳转），然后在cookie中添加openid，值任意<br/>
&emsp;然后会发现，list接口请求失败，这是因为nginx代理配置未修改<br/>
修改nginx配置：<br/>
&emsp;vim /usr/local/nginx/conf/nginx.conf<br/>
&emsp;location /sell/处将ip修改为本机ip地址（因为代码是在本地开发的，让虚拟机能够访问自己的电脑）<br/>
&emsp;nginx -s reload<br/>
因为微信限制，有时不能用ip访问，所以最好改为域名访问，方法如下（如要将服务器改为sell.com访问）：<br/>
&emsp;修改nginx配置： vim /usr/local/nginx/conf/nginx.conf<br/>
&emsp;将 server_name 改为 sell.com<br/>
&emsp;nginx -s reload<br/>
&emsp;修改本机host文件：192.168.123.200（虚拟机地址） sell.com<br/><br/>

========================以下可观看视频7.6========================<br/>
服务器配置：<br/>
&emsp;在/opt/code/sell_fe_buyer/config路径下修改index.js文件<br/>
&emsp;将openidUrl改为http://cx.s1.natapp.cc（自己的内网映射隧道名，我用的是natapp）/sell/wechat/authorize<br/>
&emsp;重新构建：<br/>
&emsp;&emsp;cd .. (回到/opt/code/sell_fe_buyer)<br/>
&emsp;&emsp;npm run build(构建好的文件在dist目录下，将该目录的所有文件拷贝到网站的根目录下)<br/>
&emsp;&emsp;cp -r dist/* /opt/data/wwwroot/sell/ （然后全填y）<br/>
&emsp;然后即可使用手机微信端访问服务器地址：192.168.123.200<br/>
&emsp;注意：虚拟机必须同网段才能访问，所以要使用代理（需要使用fiddler工具，将手机所有请求转发到电脑，通过电脑访问详情见7.6）或者手机和电脑连接同一wifi<br/><br/>

========================以下可观看视频8.4========================<br/>
为了使支付按钮有效，需要在服务器配置文件中更改配置：<br/>
&emsp;在/opt/code/sell_fe_buyer/config路径下修改index.js文件<br/>
&emsp;&emsp;将wechatPayUrl改为http://cx.s1.natapp.cc（自己的内网映射隧道名）/sell/pay/create<br/>
&emsp;重新构建：<br/>
&emsp;&emsp;cd .. (回到/opt/code/sell_fe_buyer)<br/>
&emsp;&emsp;npm run build(构建好的文件在dist目录下，将该目录的所有文件拷贝到网站的根目录下)<br/>
&emsp;&emsp;cp -r dist/* /opt/data/wwwroot/sell/ （然后全填y）<br/>

========================以下可观看视频8.7========================<br/>
退款接口需要下载证书，证书下载地址为：商户平台->账户中心->API安全->API证书<br/>
使用p12结尾的证书 并放到服务器var/weixin_cert（该路径为自己定义的，和配置文件中的keyPath相同即可）目录中<br/><br/>

========================以下可观看视频9.2========================<br/>
ibootstrap引用链接，引用后缀为bootstrap.min.css即可<br/><br/>

========================以下可观看视频11.3========================<br/>
为什么图片上传不使用上传文件的方式？<br/>
&emsp;因为该项目需要支持分布式，使用上传文件的方式只能上传到某一台服务器上（所以，要么使用第三方CDN存储，要么专门搭一台文件服务器，所以简化处理了）<br/><br/>

========================以下可观看视频12.5========================<br/>
PC端微信扫码登录功能不可用<br/>
&emsp;因为微信开放平台需要提供公司认证，并花300元认证才能获取openAppId和openAppSecret，所以本项目扫码登录部分没有校验，无法保证正确性<br/>

========================以下可观看视频12.7========================<br/>
登录网址为：http://cx.s1.natapp.cc/sell/wechat/qrAuthorize?returnUrl=http://cx.s1.natapp.cc/sell/seller/login<br/>
登录成功后即跳转到http://cx.s1.natapp.cc/sell/seller/login?openid=xxx，成功获取openid<br/>
**注意**：第一次登录会失败，因为数据库里没有该用户的openid，所以需要手动添加openid进数据库后才能登录成功<br/><br/>

========================以下可观看视频13.8========================<br/>
个人感觉RedisLock类中的unlock方法写的有问题
&emsp;在加锁时，假如有多个进程执行了getAndSet(key, value)方法，则key的value是最后一个进程的，但实际上真正获得锁的是第一个进程<br/>
&emsp;所以在解锁时，可能redis的value和实际value不同，**个人认为**：解锁时不用验证value是否相当，直接解锁就行<br/>
&emsp;因为只有获得锁才有资格继续往下执行，才有可能执行解锁操作，既然获得了锁，那么自然也有资格解锁了。<br/><br/>

========================以下可观看视频13.9========================<br/>
引用插件GenerateSerialVersionUID，可以去File->setting->Plugins中下载，可以通过快捷键自动生成唯一序列化ID，也可自己写，但较麻烦<br/><br/>

**最后注意**：因为项目后台系统验证身份时会跳转到外网映射地址，而外网映射工具映射的是本地IP，而非服务器端IP<br/>
&emsp;&emsp;所以，想要成功运行项目，要么就在服务端设置外网映射，要么更改源代码，全指向127.0.0.1，而不指向外网映射网址，要么运行本地项目<br/>