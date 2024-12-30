# アップロードサンプル

## 概要

Javaで書かれた、Servlet/JSP向け、JavaScript未使用のアップロードサンプルプログラムです。


## 動作環境

[Pleiades All in One Eclipse 2022](https://willbrains.jp/index.html#/pleiades_distros2022.html)

## 使い方

[http://localhost:8080/upload_example/upload](http://localhost:8080/upload_example/upload)

## 注意事項

- **セキュリティに対する配慮がありません(インターネットに公開された場所で動かさないでください)**
- **著作権に対する配慮がありません(インターネットに公開された場所で動かさないでください)**
- 普通に動かすと、デプロイ先のディレクトリにアップロード用のディレクトリを用意することになるので、画像がすぐに反映されません。特にファイル数が増えたり、大きなファイルがあると顕著になります。

## アップロード用ディレクトリの定義方法

本来デプロイ先にアップロード用ディレクトリを置くとパフォーマンス・セキュリティ上の大きな問題が発生する。なので、tomcatのデプロイ先でないディレクトリを外部に用意し、アプリに環境変数で伝え、静的ファイルをtomcatが配布するなら、tomcatにもリソース設定をする必要がある。

###(1) 環境変数設定

JVM起動時(tomcat起動時)に環境変数「UPLOAD_LOCATION」にフルパスでアップロード用ディレクトリを指定する。eclipse上では以下の操作。

1. 「サーバー」タブで「Tomcat9_Java17」をダブルクリック
1. 「一般情報」の「起動構成を開く」をクリック
1. 「環境」で「UPLOAD_LOCATION」という変数を追加し、値に例えば「C:\upload\」(アップロード用ディレクトリ)を設定する


###(2) tomcatサーバー設定にリソース追加

tomcatの設定ファイル「server.xml」でContextが並んでいるところに下記を追加して(例ではC:\upload\をアップロード用フォルダとしている)再起動する。

```xml
<Context docBase="C:\upload\" path="/static/uploaded"/>
```

## ライセンス

libにあるjarを除き、Apache License 2.0とします。