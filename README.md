# Request를 보내는 방법

``` json
{
    "organizationType": "MUNICIPALITY",
    "region": "경상남도",
    "group": "의령군",

    "structureType": "LISTED_CONTENT",

    "lstType": "NO_ONCLICK",
    "parentLstIdentifier": "htoListCnt",
    "parentLstTagType": "LI",
    "childLstIdentifier": "",
    "childLstTagType": "A",
    "lstOrdinalNumber": 1,

    "backType": "",
    "parentBackIdentifier": "",
    "parentBackTagType": "",
    "childBackIdentifier": "",
    "childBackTagType": "",
    "backOrdinalNumber": "",

    "pageUrl": "https://www.uiryeong.go.kr/board/list.uiryeong?boardId=BBS_0000346&menuCd=DOM_000000203002001000&contentsSid=191",
    "totalPage": 4,

    "parentPdfIdentifier": "gnb",
    "parentPdfTagType": "DIV",
    "childPdfIdentifier": "",
    "childPdfTagType": "A",
    "pdfOrdinalNumber": 1,

    "parentTitleIdentifier": "photoInfoBox",
    "parentTitleTagType": "DIV",
    "childTitleIdentifier": "photoTitle",
    "childTitleTagType": "P",
    "titleOrdinalNumber": 1,
    
    "nextPageType": "NO_ONCLICK",
    "nextIdentifier": "bdNumWrap"
}
```
> 위와 같이 저장할 객체를 보내준다.
>   > 보내주는 String 중 대문자로 표기된건 enum의 정해진 변수를 지켜야 한다.
>   >   > (back, Identifier)

# [*ENUM*]

## organizationType
<table>
    <tr>
        <th> 지자체 </th>
        <th> 문화제단 </th>
    </tr>
    <tr>
        <th> MUNICIPALITY </th>
        <th> CULTURE_FOUNDATION </th>
    </tr>
</table>

## structureType
<table>
    <tr>
        <th> 본 페이지에 다 나오는 경우 </th>
        <th> 연도별로 선택을 해야하는 경우 </th>
        <th> 글목록 형식으로 되어 있는 경우 </th>
    </tr>
    <tr>
        <th>  </th>
        <th>  </th>
    </tr>
</table>