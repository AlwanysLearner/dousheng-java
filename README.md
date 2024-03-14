# dousheng-java

目前实现基本的登陆注册、jwt验证登录、密码加密、session等机制。

基本实现视频模块。
基本实现无论用户是否登录都可以观看视频，查看评论、发表评论。
实现视频上传，点赞，取消点赞功能。
实现简易的用户画像推送视频，通过用户喜好关键词和视频关键词的匹配进行视频推送。
基本的思路是根据滑动窗口思想，根据用户最近观看的视频关键字更新用户画像。

用户一访问就会根据用户画像进行选取20个视频，将视频id存储在redis中，随后用户根据下标对进行视频读取
加下标前面的作为历史记录，下标后面的作为放送列表，每次视频出现不够的情况，就会重新根据之前看的20个视频，更新用户
画像，并重新抓取20个视频，以此类推。有使用简易的概率跳出，让用户有概率进行破圈接触到新的内容。本质上就是会随机给
用户一个新的关键词。
有做简单的热点视频视频。

有做简单的聊天模块，但java中聊天模块(websocket)需要前端进行订阅，所以未详细开发。