<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags/input" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
            <li class="active">Sekema</li>
        </ol>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Sekema Form</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="col-md-12">
                        <util:message message="${message}" messages="${messages}"/>

                        <s:url value="${saveAction}" var="url_form_submit"/>
                        <form:form modelAttribute="sekema" class="form-horizontal" method="POST" action="${url_form_submit}">
                            <form:errors path="*" cssClass="alert alert-danger" element="div"/>

                            <c:if test="${mode != 'create'}">
                                <form:hidden path="id"/>
                            </c:if>

                            <div class="form-group">
                                <!-- The field label is defined in the messages file (for i18n) -->
                                <label for="kode">Kode</label>

                                <form:input id="kode" path="kode" class="form-control"/>
                                <form:errors id="kode_errors" path="kode" cssClass="label label-danger"/>
                            </div>

                            <div class="form-group">
                                <!-- The field label is defined in the messages file (for i18n) -->
                                <label for="nama">Nama</label>
                                <form:input id="nama" path="nama" class="form-control"/>
                                <form:errors id="nama_errors" path="nama" cssClass="label label-danger"/>
                            </div>

                            <div class="form-group">
                                <!-- The field label is defined in the messages file (for i18n) -->
                                <label for="modal">Modal</label>
                                <select id="modal" class="form-control">
                                    <option value="">--Select--</option>
                                    <c:forEach items="${modalList}" var="item">
                                        <option value="${item.id}">${item.nama}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="jumlah">Jumlah</label>
                                <input id="jumlah" value="1" class="form-control"/>
                            </div>

                            <div class="form-group">
                                <a class="btn btn-primary" href="#" onclick="addToTable(this)">ADD</a>
                            </div>

                            <div class="form-group">
                                <table id="tableModal"
                                       data-toggle="table">
                                    <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false">ID</th>
                                        <th data-field="nama" >Nama Barang</th>
                                        <th data-field="satuan" >Satuan</th>
                                        <th data-field="jumlah" >jumlah</th>
                                        <th data-field="harga" >Harga</th>
                                        <th data-field="total" >Total</th>
                                        <th data-formatter="actionFormatSekema">Action</th>
                                    </tr>
                                    </thead>
                                    <tbody style="right: auto"/>
                                </table>
                            </div>

                            <div class="form-group">
                                <!-- The field label is defined in the messages file (for i18n) -->
                                <label for="totalModal">Total</label>
                                <form:input id="totalModal" path="totalModal" class="form-control"/>
                                <form:errors id="totalModal_errors" path="totalModal" cssClass="label label-danger"/>
                            </div>

                            <!-- ACTION BUTTONS -->
                            <div class="form-group">
                                <div class="col-sm-2">
                                    <c:if test="${mode != 'create'}">
                                        <!-- "DELETE" button ( HREF link ) -->
                                        <s:url var="deleteButtonURL" value="/sekema/delete/${sekema.id}"/>
                                        <a role="button" class="btn btn-danger btn-block"
                                           href="${deleteButtonURL}"><s:message code="delete"/></a>
                                    </c:if>
                                </div>
                                <div class="col-sm-offset-6 col-sm-2">
                                    <!-- "CANCEL" button ( HREF link ) -->
                                    <s:url var="cancelButtonURL" value="/sekema"/>
                                    <a role="button" class="btn btn-default btn-block"
                                       href="${cancelButtonURL}"><s:message code="cancel"/></a>
                                </div>
                                <div class="col-sm-2">
                                    <!-- "SAVE" button ( SUBMIT button ) -->
                                    <a type="button"  href="#" onclick="saveSekema(this)" class="btn btn-primary btn-lg btn-block"><s:message code="save"/></a>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">

        window.onload = function() {
            var mode = "${mode}";
            if(mode != 'create' ){
                <c:forEach items="${sekema.listOfSekemaDetails}" var="item">
                $('#tableModal').bootstrapTable('insertRow', {
                    index: 0,
                    row: {
                        id: '${item.id}',
                        nama: '${item.nama}',
                        satuan: '${item.satuan}',
                        jumlah: '${item.jumlah}',
                        harga: '${item.harga}',
                        total: '${item.harga}'*'${item.jumlah}'
                    }
                });
                </c:forEach>
            }
        }

        function actionFormatSekema(value, row, index) {
            return  '<a type="button" class="btn btn-danger btn-sm" href="#" onclick="deleteSekema(this)">Delete</a> ';
        }

        function addToTable(params){
            if($('#modal').val() == "" || $('#jumlah').val() == ""){
                alert('Modal and Jumlah is Required');
            }else{
                var input = new Object();
                input = {
                    "id": $('#modal').val(),
                    "jumlah": $('#jumlah').val()
                };

                $.ajax({
                    url: ${pageContext.request.contextPath}"/sekema/get-modal-by-id",
                    type: 'POST',
                    data: JSON.stringify(input),
                    dataType: 'json',
                    contentType: 'application/json',
                    async: false,
                    success:function(response) {
                        if(response){
                            $('#tableModal').bootstrapTable('insertRow', {
                                index: 0,
                                row: {
                                    id: response.id,
                                    nama: response.nama,
                                    satuan: response.satuan,
                                    jumlah: response.jumlah,
                                    harga: response.harga,
                                    total: response.total
                                }
                            });
                        }
                        totalGlobalSekema(params);
                        $('#modal').val('');
                        $('#jumlah').val('1');
                    },
                    error:function(jqXHR, textStatus, errorThrown){
                        alert('internal server error');
                    }
                });
            }
        }

        function totalGlobalSekema(params){
            var rowData = $('#tableModal').bootstrapTable('getData');
            var totalGlobal = 0;
            for (var i = 0; i < rowData.length; i++) {
                totalGlobal=totalGlobal+rowData[i].total;
            }
            $('#totalModal').val(totalGlobal);
        }

        function deleteSekema(obj){
            var indexData = $(obj).closest('tr').data('index');
            var row = $('#tableModal').bootstrapTable('getData')[indexData];
            $('#tableModal').bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
            });
            totalGlobalSekema(obj);
        }

        function saveSekema(obj){
            var rowData = $('#tableModal').bootstrapTable('getData');

            var input = new Object();
            input = {
                "id": '${sekema.id}',
                "nama": $('#nama').val(),
                "kode": $('#kode').val(),
                "totalModal": $('#totalModal').val(),
                "mode": '${mode}',
                "listOfSekemaDetails" : rowData
            };


            $.ajax({
                url: ${pageContext.request.contextPath}"/sekema/save-jquery",
                type: 'POST',
                data: JSON.stringify(input),
                dataType: 'json',
                contentType: 'application/json',
                async: false,
                success:function(response) {
                    if(response===1){
                        alert('Saved successfully');
                        window.location.href = ${pageContext.request.contextPath}"/sekema";

                    }
                },
                error:function(jqXHR, textStatus, errorThrown){
                    alert('internal server error');
                }
            });
        }
    </script>
</div>
