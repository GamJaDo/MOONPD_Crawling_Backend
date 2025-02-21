package Intern.moonpd_crawling.dto.request;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.PdfType;
import Intern.moonpd_crawling.status.TitleType;
import Intern.moonpd_crawling.status.YearType;
import Intern.moonpd_crawling.status.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.child.ChildLstTagType;
import Intern.moonpd_crawling.status.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.child.ChildYearTagType;
import Intern.moonpd_crawling.status.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.NextPageType;
import Intern.moonpd_crawling.status.OrganizationType;
import Intern.moonpd_crawling.status.StructureType;
import Intern.moonpd_crawling.status.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.parent.ParentYearTagType;

public record TargetRegisterRequest(OrganizationType organizationType, String region, String group,
                                    StructureType structureType, LstType lstType,
                                    ParentLstTagType parentLstTagType, String parentLstIdentifier,
                                    ChildLstTagType childLstTagType, String childLstIdentifier,
                                    int lstOrdinalNumber, YearType yearType,
                                    String parentYearIdentifier,
                                    ParentYearTagType parentYearTagType,
                                    String childYearIdentifier, ChildYearTagType childYearTagType,
                                    int yearOrdinalNumber, String pageUrl,
                                    ExtendedPdfType extendedPdfType,
                                    String parentExtendedPdfIdentifier,
                                    ParentExtendedPdfTagType parentExtendedPdfTagType,
                                    int extendedPdfOrdinalNumber, PdfType pdfType,
                                    String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
                                    String childPdfIdentifier, ChildPdfTagType childPdfTagType,
                                    int pdfOrdinalNumber, TitleType titleType,
                                    String parentTitleIdentifier,
                                    ParentTitleTagType parentTitleTagType,
                                    String childTitleIdentifier,
                                    ChildTitleTagType childTitleTagType,
                                    int titleOrdinalNumber, NextPageType nextPageType,
                                    String parentNextPageIdentifier,
                                    ParentNextPageTagType parentNextPageTagType,
                                    String childNextPageIdentifier,
                                    ChildNextPageTagType childNextPageTagType,
                                    int nextPageOrdinalNumber) {

    public Target toEntity() {
        return new Target(organizationType, region, group, structureType,
            lstType, parentLstTagType, parentLstIdentifier, childLstTagType, childLstIdentifier,
            lstOrdinalNumber, yearType, parentYearIdentifier, parentYearTagType,
            childYearIdentifier, childYearTagType, yearOrdinalNumber, pageUrl, extendedPdfType,
            parentExtendedPdfIdentifier, parentExtendedPdfTagType, extendedPdfOrdinalNumber,
            pdfType, parentPdfIdentifier, parentPdfTagType, childPdfIdentifier, childPdfTagType,
            pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
            childTitleIdentifier, childTitleTagType, titleOrdinalNumber, nextPageType,
            parentNextPageIdentifier, parentNextPageTagType, childNextPageIdentifier,
            childNextPageTagType, nextPageOrdinalNumber);
    }
}
