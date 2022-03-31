<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex justify-content-center">
	<div class="contents-box">
		
		<%-- 글쓰기 영역 - 로그인 된 상태에서만 보임 --%>
		<c:if test="${not empty userId}">
			<%-- textarea의 테두리는 없애고 div에 테두리를 준다. --%>
			<div class="write-box border rounded m-3">
				<textarea id="writeTextArea" placeholder="내용을 입력해주세요" class="w-100 border-0"></textarea>
				
				<%-- 이미지 업로드를 위한 아이콘과 업로드 버튼을 한 행에 멀리 떨어뜨리기 위한 div --%>
				<div class="d-flex justify-content-between">
					<div class="file-upload d-flex">
						<%-- file 태그는 숨겨두고 이미지를 클릭하면 file 태그를 클릭한 것처럼 이벤트를 줄 것이다. --%>
						<input type="file" id="file" class="d-none" accept=".gif, .jpg, .png, .jpeg">
						<%-- 이미지에 마우스 올리면 마우스커서가 링크 커서가 변하도록 a 태그 사용 --%>
						<a href="#" id="fileUploadBtn"><img width="35" src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png"></a>

						<%-- 업로드 된 임시 파일 이름 저장될 곳 --%>
						<div id="fileName" class="ml-2">
						</div>
					</div>
					<button id="writeBtn" class="btn btn-info">게시</button>
				</div>
			</div>
		</c:if>
		
		<%-- 타임라인 영역 --%>
		<%-- my: margin 위아래(y축) --%>
		<div class="timeline-box my-5">
			<c:forEach items="${cardViewList}" var="card">
				<%-- 카드 하나하나마다 영역을 border로 나눔 --%>
				<div class="card border rounded mt-3">
					
					<%-- 글쓴이명, 삭제버튼 --%>
					<div class="p-2 d-flex justify-content-between">
						<span class="font-weight-bold">${card.user.name}</span>
						
						<%-- 글쓴이와 로그인된 사용자가 일치할 경우 버튼 노출 --%>
						<c:if test="${card.user.id eq userId}">
						<a href="#" class="more-btn" data-toggle="modal" data-target="#moreModal" data-post-id="${card.post.id}">
							<img src="https://www.iconninja.com/files/860/824/939/more-icon.png" width="30">
						</a>
						</c:if>
					</div>
					
					<%-- 카드 이미지 --%>
					<div class="card-img">
						<%-- 이미지가 존재하는 경우에만 노출 --%>
							<img src="${card.post.imagePath}" class="w-100" alt="이미지">
					</div>
					
					<%-- 좋아요 --%>
					<div class="card-like m-3">
						<a href="#" class="like-btn" data-post-id="${card.post.id}">
							<%-- 좋아요 해제 상태 --%>
							<c:if test="${card.filledLike eq false}">
								<img src="https://www.iconninja.com/files/214/518/441/heart-icon.png" width="18px" height="18px" alt="empty heart">
							</c:if>
							<%-- 좋아요 상태 --%>
							<c:if test="${card.filledLike eq true}">
								<img src="https://www.iconninja.com/files/527/809/128/heart-icon.png" width="18px" height="18px" alt="filled heart">
							</c:if>
						
						좋아요 ${card.likeCount}개
						</a>
					</div>
					
					<%-- 글(Post) --%>
					<div class="card-post m-3">
						<span class="font-weight-bold">${card.user.name}</span> 
						<span>
							${card.post.content}
						</span>
					</div>
					
					<%-- 댓글(Comment) --%>
					
					<%-- "댓글" - 댓글이 있는 경우에만 댓글 영역 노출 --%>
					<c:if test="${not empty card.commentList}">
						<div class="card-comment-desc border-bottom">
							<div class="ml-3 mb-1 font-weight-bold">댓글</div>
						</div>
						<div class="card-comment-list m-2">
							<%-- 댓글 목록 --%>
							<c:forEach items="${card.commentList}" var="commentView">
								<div class="card-comment m-1">
									<span class="font-weight-bold">${commentView.user.name} : </span>
									<span>${commentView.comment.content}</span>
									
									<%-- 댓글쓴이가 본인이면 삭제버튼 노출 --%>
									<c:if test="${commentView.user.id eq userId}">
										<a href="#" class="commentDelBtn">
											<img src="https://www.iconninja.com/files/603/22/506/x-icon.png" width="10px" height="10px">
										</a>
									</c:if>
								</div>
							</c:forEach>
						</div>
					</c:if>
					
					<%-- 댓글 쓰기 --%>
					<%-- 로그인 된 상태에서만 쓸 수 있다. --%>
					<c:if test="${not empty userId}">
						<div class="comment-write d-flex border-top mt-2">
							<input type="text" id="comment${card.post.id}" class="form-control border-0 mr-2" placeholder="댓글 달기"/> 
							<button type="button" class="commentBtn btn btn-light" data-post-id="${card.post.id}">게시</button>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
	</div>
</div>    


<!-- Modal -->
<div class="modal fade" id="moreModal">
	<div class="modal-dialog modal-sm modal-dialog-centered" role="document">
		<div class="modal-content">
      		<div class="text-center">
      			<div class="my-3">
      				<a href="#" id="postDeleteBtn" class="d-block">삭제하기</a>
      			</div>
      			<hr>
      			<div class="my-3">
      				<a href="#" class="d-block" data-dismiss="modal">취소</a>
      			</div>
      		</div>
		</div>
	</div>
</div>


<script>
$(document).ready(function() {
	// 파일업로드 이미지 클릭 => 파일 선택 창이 떠야함
	$('#fileUploadBtn').on('click', function(e) {
		e.preventDefault(); // 위로 올라가는 현상 방지
		$('#file').click(); // input file을 클릭한 것과 같은 효과
	});
	
	// 사용자가 파일 업로드를 했을 때 유효성 확인 및 업로드 된 파일 이름 노출
	$('#file').on('change', function(e) {
		//alert("체인지");
		let fileName = e.target.files[0].name;
		//alert(fileName);
		let fileArr = fileName.split(".");
		
		// 확장자 유효성 체크
		if (fileArr.length < 2 || 
				(fileArr[fileArr.length - 1] != 'gif'
				&& fileArr[fileArr.length - 1] != 'png'
				&& fileArr[fileArr.length - 1] != 'jpeg'
				&& fileArr[fileArr.length - 1] != 'jpg')) {
			alert("이미지 파일만 업로드 해주세요.");
			$(this).val(''); // 파일이 서버에 넘어가지 않도록 비워줌
			$('#fileName').text(''); // 파일명도 비워줌
			return;
		}
		
		$('#fileName').text(fileName);
	});
	
	// 글 내용 저장
	$('#writeBtn').on('click', function(e) {
		// validation 
		let content = $('#writeTextArea').val();
		console.log(content);
		if (content.length < 1) {
			alert("글 내용을 입력해주세요");
		}
		
		// 파일이 업로드 된 경우 확장자 체크
		let file = $('#file').val();  // 파일 경로만 가져온다.
		console.log(file);  // C:\fakepath\image.png
		if (file != '') {
			let ext = file.split('.').pop().toLowerCase(); // 파일 경로를 .으로 나누고 확장자가 있는 마지막 문자열을 가져온 후 모두 소문자로 변경
			if ($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
				alert("gif, png, jpg, jpeg 파일만 업로드 할 수 있습니다.");
				$('#file').val(''); // 파일을 비운다.
				return;
			}
		}
		
		// 폼태그를 자바스크립트에서 만든다.
		let formData = new FormData();
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]); // $('#file')[0]은 첫번째 input file 태그를 의미, files[0]는 업로드된 첫번째 파일
		
		// AJAX form 데이터 전송
		$.ajax({
			type: "post"
			, url: "/post/create"
			, data: formData
			, enctype: "multipart/form-data"    // 파일 업로드를 위한 필수 설정
			, processData: false    // 파일 업로드를 위한 필수 설정
			, contentType: false    // 파일 업로드를 위한 필수 설정
			, success: function(data) {
				if (data.result == "success") {
					location.reload();
				} else {
					alert(data.errorMessage);
				}
			}
			, error: function(e) {
				alert("메모 저장에 실패했습니다. 관리자에게 문의해주세요.");
			}
		});
	});
	
	// 댓글 쓰기
	$('.commentBtn').on('click', function() {
		let postId = $(this).data('post-id');
		//alert(postId);
		
		//let commentContent = $('#comment' + postId).val().trim();
		let commentContent = $(this).siblings('input').val().trim();
		//alert(commentContent);
		
		if (commentContent == '') {
			alert("댓글 내용을 입력해주세요");
			return;
		}
		
		// ajax 
		$.ajax({
			type:'POST',
			url:'/comment/create',
			data: {"postId":postId, "content":commentContent},
			success: function(data) {
				if (data.result == 'success') {
					location.reload(); // 새로고침
				} else {
					alert(data.error_message);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}
		});
	});
	
	// 좋아요/해제 - 하트 버튼 클릭
	$('.like-btn').on('click', function(e) {
		e.preventDefault(); // a 태그 동작 중단
		
		let postId = $(this).data('post-id');
		//alert(postId);
		
		$.ajax({
			url: "/like/" + postId
			,success: function(data) {
				if (data.result == "success") {
					location.reload();
				} else {
					alert(data.error_message);
				}
			}
			,error: function(e) {
				alert("좋아요가 실패했습니다. 관리자에게 문의해주세요.");
			}
		});
	});
	
	// 카드에서 더보기(...) 클릭할 때 삭제될 글번호를 모달에 넣어준다.
	$('.more-btn').on('click', function() {
		let postId = $(this).data('post-id');
		//alert(postId);
		
		$('#moreModal').data('post-id', postId);  // data-post-id="1"
	});
	
	// 모달창 안에 있는 '삭제하기' 글자 클릭
	$('#moreModal #postDeleteBtn').on('click', function(e) {
		e.preventDefault();
		
		let postId = $('#moreModal').data('post-id');
		//alert(postId);
		
		$.ajax({
			type:"DELETE"
			, url:"/post/delete"
			, data: {"postId":postId}
			, success:function(data) {
				if (data.result == "success") {
					location.reload();
				} else {
					alert(data.error_message);
				}
			}
			, error:function(e) {
				alert("삭제하는데 실패했습니다. 관리자에게 문의해주세요.");
			}
		});
	});
});
</script>


