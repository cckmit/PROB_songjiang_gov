# PROB_jiashan_gov
# 嘉善政府（后端java）


* 开发分支 dev<br>
* 测试分支 test<br>
* 生产分支 release<br>



* 下载项目：


* 切换分支：
```
git checkout <分支名>     //取出<分支名>版本的head。
git checkout tag_name    //在当前分支上 取出 tag_name 的版本
```

* 合并分支：
```
在将Develop分支发布到Release分支时，可能采用如下的命令
# 切换到Release分支
git checkout <Release>
# 对Develop分支进行合并
git merge --no-ff <Develop>
```

* 推送代码：
```
git push <远程主机名> <本地分支名>:<远程分支名>
```

## 版本发布

### v0.2 2017/11/10
