package Intern.moonpd_crawling.dto.request;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.status.BackType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.child.ChildBackTagType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentBackTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.OrganizationType;
import Intern.moonpd_crawling.status.StructureType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;

public record TargetUpdateRequest(OrganizationType organizationType, String region, String group,
                                  StructureType structureType, LstType lstType,
                                  ParentLstTagType parentLstTagType, String parentLstIdentifier,
                                  ChildLstTagType childLstTagType, String childLstIdentifier,
                                  int lstOrdinalNumber,
                                  BackType backType, ParentBackTagType parentBackTagType,
                                  String parentBackIdentifier, ChildBackTagType childBackTagType,
                                  String childBackIdentifier, int backOrdinalNumber,
                                  String pageUrl, int totalPage,
                                  String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
                                  String childPdfIdentifier, ChildPdfTagType childPdfTagType,
                                  int pdfOrdinalNumber, int titleOrdinalNumber,
                                  String parentTitleIdentifier,
                                  ParentTitleTagType parentTitleTagType,
                                  String childTitleIdentifier,
                                  ChildTitleTagType childTitleTagType,
                                  NextPageType nextPageType, String nextIdentifier) {

    public Target toEntity() {
        return new Target(organizationType, region, group, structureType, lstType,
            parentLstTagType, parentLstIdentifier, childLstTagType, childLstIdentifier,
            lstOrdinalNumber, backType, parentBackTagType, parentBackIdentifier,
            childBackTagType, childBackIdentifier, backOrdinalNumber,
            pageUrl, totalPage,
            parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, titleOrdinalNumber, parentTitleIdentifier, parentTitleTagType,
            childTitleIdentifier, childTitleTagType,
            nextPageType, nextIdentifier);
    }
}
