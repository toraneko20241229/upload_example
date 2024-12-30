<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アップロードサンプル</title>
</head>
<body>
	<c:if test="${!empty uploaded_path}">
		<h2>アップロード結果</h2>
		<div>アップロードされたファイルは${uploaded_path}に保存されました。</div>
		<div>
			<a href="${download_url}">ダウンロード</a>(保存先がデプロイ先の場合、反映に時間がかかります)
		</div>
	</c:if>
	<div>
		<h2>アップロード画像リスト</h2>
		<c:forEach var="file" items="${filelist}">
			<img src="${file}" />
		</c:forEach>
	</div>
	<div>
		<h2>アップロード</h2>
		<form action="./upload" method="post" enctype="multipart/form-data">
			<div>
				ファイルを選択してください: <input type="file" name="file" multiple="true" /><br />
			</div>
			<div>
				<input type="submit" value="送信" />
			</div>
		</form>
	</div>
</body>
</html>