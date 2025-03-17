$(function () {
    // Initialize Select2 Elements
    $('.select2').select2({
        allowClear: true,  // 允许清除选择
        placeholder: "请选择专辑...",  // 设置默认提示文字
    });

    // Handle cover image upload
    $('#vinylCover').on('change', function (event) {
        var file = event.target.files[0];
        if (file) {
            var formData = new FormData();
            formData.append('file', file);
            $.ajax({
                url: '/admin/upload/file',
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    if (response.resultCode == 200) {
                        $('#coverPreview').attr('src', response.data).show();
                        $('#vinylCover').siblings('.custom-file-label').text(file.name);
                    } else {
                        swal(response.message, {
                            icon: "error",
                        });
                    }
                },
                error: function () {
                    swal("上传失败", {
                        icon: "error",
                    });
                }
            });
        }
    });

    // Save button click event
    $('#confirmVinylButton').click(function () { // 修改保存按钮的ID
        // 表单参数校验
        var singleName = $('#singleName').val().trim();
        var vinylTitle = $('#vinylTitle').val().trim();
        var artist = $('#artist').val().trim();
        var issueDate = $('#issueDate').val().trim();
        var price = $('#price').val().trim();
        var vinylCoverImage = $('#vinylCoverImage')[0].src;

        if (!singleName) {
            swal('单曲名称不能为空', {
                icon: "error",
            });
            return;
        }

        if (!vinylTitle) {
            swal('唱片标题不能为空', {
                icon: "error",
            });
            return;
        }

        if (!artist) {
            swal('艺术家不能为空', {
                icon: "error",
            });
            return;
        }

        if (!issueDate) {
            swal('发行日期不能为空', {
                icon: "error",
            });
            return;
        }

        if (!price) {
            swal('价格不能为空', {
                icon: "error",
            });
            return;
        }

        if (isNull(vinylCoverImage) || vinylCoverImage.indexOf('img-upload') !== -1) {
            swal('唱片封面不能为空', {
                icon: "error",
            });
            return;
        }

        // 确保专辑字段为空时，不会发送默认值
        var albumId = $('#albumId').val();

        var data = {
            id: $('#vinylForm').find('input[name="vinylId"]').val(), // 修改选择器为 vinylId
            singleName: singleName,
            vinylTitle: vinylTitle,
            artist: artist,
            albumId: albumId, // 直接使用 albumId，不需要额外处理
            issueDate: issueDate,
            price: price,
            vinylCover: vinylCoverImage
        };

        var url = data.id ? '/vinyl/update' : '/vinyl/save';
        var swlMessage = data.id ? '修改成功' : '保存成功';

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(data), // 修改数据格式为JSON
            contentType: 'application/json', // 修改内容类型为JSON
            processData: false, // 禁用数据处理
            success: function (result) {
                if (result.code === 200) {
                    swal({
                        title: swlMessage,
                        type: 'success',
                        showCancelButton: false,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: '返回唱片列表',
                        confirmButtonClass: 'btn btn-success',
                        buttonsStyling: false
                    }).then(function () {
                        window.location.href = "/page/vinyl";
                    });
                } else {
                    swal(result.message, {
                        icon: "error",
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    });

    // Cancel button click event
    $('#cancelVinylButton').click(function () { // 修改取消按钮的ID
        window.location.href = "/page/vinyl";
    });



    $(document).ready(function(){
        // 获取隐藏域中的selectedAlbumId值，并将其转换为字符串
        var selectedAlbumId = $('#selectedAlbumId').val();

        // 获取专辑数据并填充到下拉框
        $.ajax({
            url: '/album/select',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                var select = $('#albumId');
                data.data.forEach(function (album) {
                    // 创建option元素
                    var option = $('<option>', {
                        value: album.id,
                        text: album.albumName
                    }).appendTo(select); // 添加到select元素

                    // 检查是否需要选中此项，注意这里将两边都转换为字符串进行比较
                    if (String(album.id) === String(selectedAlbumId)) {
                        option.prop('selected', true); // 设置为选中状态
                    }
                });

                // 如果使用了select2插件，记得初始化
                $('#albumId').select2();
            },
            error: function (xhr, status, error) {
                console.error('Error fetching album data:', error);
            }
        });
    });

});

/**
 * 随机封面功能
 */
$('#randomCoverImage').click(function () {
    var rand = parseInt(Math.random() * 40 + 1);
    $("#vinylCoverImage").attr("src", '/admin/dist/img/rand/' + rand + ".jpg"); // 修改图片选择器
    $("#vinylCoverImage").attr("style", "width:160px ;height: 120px;display:block;");
});

$('#uploadVinylCoverImage').click(function (event) {
    event.preventDefault(); // 阻止默认行为，防止表单提交或事件冒泡导致模态框关闭
    var fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.accept = 'image/jpg,image/jpeg,image/png,image/gif';
    fileInput.onchange = function () {
        var file = fileInput.files[0];
        if (file) {
            var validExtensions = ['jpg', 'jpeg', 'png', 'gif'];
            var extension = file.name.split('.').pop().toLowerCase();
            if (validExtensions.indexOf(extension) === -1) {
                alert('只支持jpg、png、gif格式的文件！');
                return;
            }

            var formData = new FormData();
            formData.append('file', file);

            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/common/upload/file', true);
            xhr.setRequestHeader('Authorization', getTokenFromSession());
            xhr.responseType = 'json';

            xhr.onload = function () {
                if (xhr.status === 200) {
                    var r = xhr.response;
                    if (r != null && r.code === 200) {
                        console.log("上传成功，图片URL:", r.data); // 添加调试信息
                        $("#vinylCoverImage").attr("src", r.data); // 修改选择器为 #vinylCoverImage
                        $('#vinylCoverImage').attr("style", "width: 128px;height: 128px;display:block;");
                    } else {
                        alert("上传失败");
                    }
                } else {
                    alert("上传失败");
                }
            };

            xhr.send(formData);
        }
    };
    fileInput.click();
});