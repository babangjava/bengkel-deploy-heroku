<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-signal"></span> APP-NAME<span></span></a>
            <ul class="user-menu">
                <li class="dropdown pull-right">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
                        <!-- ====================================================LOGIN -->
                        ADMIN <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="ganti-password.php"><span class="glyphicon glyphicon-user"></span> Ganti password</a></li>
                        <!--<li><a href="#"><span class="glyphicon glyphicon-cog"></span> Settings</a></li>-->
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>

    </div>
</nav>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">

    <ul class="nav menu">
        <li class="active"><a href="/"><span class="glyphicon glyphicon-dashboard"></span> Dashboard</a></li>


        <li class="parent ">
            <a href="#">
                <span data-toggle="collapse" href="#sub-item-1" class="glyphicon glyphicon-th"></span> Master <span data-toggle="collapse" href="#sub-item-1" class="icon pull-right"><em class="glyphicon glyphicon-chevron-down"></em></span>
            </a>
            <ul class="children collapse" id="sub-item-1">
                <li>
                    <a class="" href="${pageContext.request.contextPath}/modal">
                        <span class="glyphicon glyphicon-tasks"></span> Modal
                    </a>
                </li>
                <li>
                    <a class="" href="${pageContext.request.contextPath}/sekema">
                        <span class="glyphicon glyphicon glyphicon-tags"></span> Sekema
                    </a>
                </li>
            </ul>
        </li>
        <li class="parent ">
            <a href="#">
                <span data-toggle="collapse" href="#sub-item-2" class="glyphicon glyphicon-transfer"></span> Transaksi <span data-toggle="collapse" href="#sub-item-2" class="icon pull-right"><em class="glyphicon glyphicon-chevron-down"></em></span>
            </a>
            <ul class="children collapse" id="sub-item-2">
                <li>
                    <a class="" href="transaksi1.php">
                        <span class="glyphicon glyphicon-arrow-right"></span> Barang Masuk
                    </a>
                </li>
                <li>
                    <a class="" href="transaksi2.php">
                        <span class="glyphicon glyphicon glyphicon-arrow-left"></span> Barang Keluar
                    </a>
                </li>
            </ul>
        </li>

        <li class="parent ">
            <a href="#">
                <span data-toggle="collapse" href="#sub-item-3" class="glyphicon glyphicon-stats"></span> Laporan <span data-toggle="collapse" href="#sub-item-3" class="icon pull-right"><em class="glyphicon glyphicon-chevron-down"></em></span>
            </a>
            <ul class="children collapse" id="sub-item-3">
                <li>
                    <a class="" href="history1.php">
                        <span class="glyphicon glyphicon-pencil"></span> History Transaksi
                    </a>
                </li>
                <li>
                    <a class="" href="laporan1.php">
                        <span class="glyphicon glyphicon glyphicon-list-alt"></span> Stok Barang
                    </a>
                </li>
            </ul>
        </li>


        <li class="parent ">
            <a href="#">
                <span data-toggle="collapse" href="#sub-item-4" class="glyphicon glyphicon-list"> </span> Administrator <span data-toggle="collapse" href="#sub-item-4" class="icon pull-right"><em class="glyphicon glyphicon-chevron-down"></em></span>
            </a>
            <ul class="children collapse" id="sub-item-4">
                <li>
                    <a class="" href="users.php">
                        <span class="glyphicon glyphicon-user"></span> Users
                    </a>
                </li>
                <li>
                    <a class="" href="gudang.php">
                        <span class="glyphicon glyphicon-home"></span> Gudang
                    </a>
                </li>
                <li>
                    <a class="" href="backup.php">
                        <span class="glyphicon glyphicon-cloud-download"></span> Backup
                    </a>
                </li>
                <li>
                    <a class="" href="restore.php">
                        <span class="glyphicon glyphicon-cloud-upload"></span> Restore
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</div>
