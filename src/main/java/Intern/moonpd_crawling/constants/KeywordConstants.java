package Intern.moonpd_crawling.constants;

public final class KeywordConstants {

    public static final String[] P_SUDO_KEYWORDS = {
        "data-idx", "data-req-get-p-idx", "data-file-key"
    };

    public static final String[] ON_CLICK_LST_KEYWORDS = {
        "fn_view(", "fn_articleLink(", "goTo.view(", "goDetail(", "fn_search_detail("
    };

    public static final String[] ON_CLICK_PDF_KEYWORDS = {
        "fn_egov_downFile", "yhLib.file.download", "location.href=",
        "pdf_download(", "openDownloadFiles(", "gfnFileDownload(", "cf_downloadPDF("
    };

    public static final String[] NEXT_PAGE_KEYWORDS = {
        "p=", "pg=", "pageIndex=", "page=", "curPage=", "pno=", "page_no=", "pgno=",
        "PAGE=", "goPage(", "noticeList(", "Page2(", "search(", "linkPage(", "pageMove(",
        "Page_func(", "Pag(", "movePage(", "Page(", "pageNo=", "Page=", "search("
    };
}
