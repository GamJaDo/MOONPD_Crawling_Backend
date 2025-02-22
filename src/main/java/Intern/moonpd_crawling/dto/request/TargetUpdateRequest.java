package Intern.moonpd_crawling.dto.request;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.status.selector.child.ChildLstSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.type.ExtendedPdfType;
import Intern.moonpd_crawling.status.type.LstType;
import Intern.moonpd_crawling.status.type.PdfType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.type.YearType;
import Intern.moonpd_crawling.status.tag.child.ChildNextPageTagType;
import Intern.moonpd_crawling.status.tag.child.ChildLstTagType;
import Intern.moonpd_crawling.status.tag.child.ChildPdfTagType;
import Intern.moonpd_crawling.status.tag.child.ChildTitleTagType;
import Intern.moonpd_crawling.status.tag.child.ChildYearTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentNextPageTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedPdfTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentLstTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentPdfTagType;
import Intern.moonpd_crawling.status.type.NextPageType;
import Intern.moonpd_crawling.status.type.OrganizationType;
import Intern.moonpd_crawling.status.type.StructureType;
import Intern.moonpd_crawling.status.tag.parent.ParentTitleTagType;
import Intern.moonpd_crawling.status.tag.parent.ParentYearTagType;

public record TargetUpdateRequest(OrganizationType organizationType, String region, String group,
                                  StructureType structureType, LstType lstType,
                                  String parentLstIdentifier, ParentLstTagType parentLstTagType,
                                  ParentLstSelectorType parentLstSelectorType,
                                  String childLstIdentifier, ChildLstTagType childLstTagType,
                                  ChildLstSelectorType childLstSelectorType,
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
                                  ParentPdfSelectorType parentPdfSelectorType,
                                  String childPdfIdentifier, ChildPdfTagType childPdfTagType,
                                  ChildPdfSelectorType childPdfSelectorType,
                                  int pdfOrdinalNumber, TitleType titleType,
                                  String parentTitleIdentifier,
                                  ParentTitleTagType parentTitleTagType,
                                  ParentTitleSelectorType parentTitleSelectorType,
                                  String childTitleIdentifier,
                                  ChildTitleTagType childTitleTagType,
                                  ChildTitleSelectorType childTitleSelectorType,
                                  int titleOrdinalNumber, NextPageType nextPageType,
                                  String parentNextPageIdentifier,
                                  ParentNextPageTagType parentNextPageTagType,
                                  String childNextPageIdentifier,
                                  ChildNextPageTagType childNextPageTagType,
                                  int nextPageOrdinalNumber) {

    public Target toEntity() {
        return new Target(organizationType, region, group, structureType, lstType,
            parentLstIdentifier, parentLstTagType, parentLstSelectorType, childLstIdentifier,
            childLstTagType, childLstSelectorType, lstOrdinalNumber, yearType, parentYearIdentifier,
            parentYearTagType, childYearIdentifier, childYearTagType, yearOrdinalNumber, pageUrl,
            extendedPdfType, parentExtendedPdfIdentifier, parentExtendedPdfTagType,
            extendedPdfOrdinalNumber, pdfType, parentPdfIdentifier, parentPdfTagType,
            parentPdfSelectorType, childPdfIdentifier, childPdfTagType, childPdfSelectorType,
            pdfOrdinalNumber, titleType, parentTitleIdentifier, parentTitleTagType,
            parentTitleSelectorType, childTitleIdentifier, childTitleTagType,
            childTitleSelectorType, titleOrdinalNumber, nextPageType, parentNextPageIdentifier,
            parentNextPageTagType, childNextPageIdentifier, childNextPageTagType,
            nextPageOrdinalNumber);
    }
}
