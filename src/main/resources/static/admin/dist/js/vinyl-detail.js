$(function () {
    // Initialize Select2 Elements
    $('.select2').select2({
        allowClear: true,  // 允许清除选择
        placeholder: "请选择专辑...",  // 设置默认提示文字
    });

    // Pre-fill form if viewing an existing vinyl
    var vinylId = new URLSearchParams(window.location.search).get('id');
    if (vinylId) {
        $.ajax({
            url: '/vinyl/detail/' + vinylId,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#vinylForm').find('input[name="id"]').val(data.id).prop('disabled', true);
                $('#singleName').val(data.singleName).prop('disabled', true);
                $('#vinylTitle').val(data.vinylTitle).prop('disabled', true);
                $('#artist').val(data.artist).prop('disabled', true);
                $('#albumId').val(data.albumId).trigger('change').prop('disabled', true);
                $('#issueDate').val(data.issueDate.split('T')[0]).prop('disabled', true);
                $('#price').val(data.price).prop('disabled', true);
                $('#vinylCoverImage').attr('src', data.vinylCover).show();
            },
            error: function (xhr, status, error) {
                console.error('Error fetching vinyl data:', error);
            }
        });
    }

    // 返回唱片列表按钮点击事件
    $('#backToListButton').click(function () {
        window.history.back();
    });
});