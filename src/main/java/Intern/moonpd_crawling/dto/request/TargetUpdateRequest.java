package Intern.moonpd_crawling.dto.request;

import Intern.moonpd_crawling.entity.Target;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.type.OrganizationType;
import Intern.moonpd_crawling.status.type.StructureType;

public record TargetUpdateRequest(OrganizationType organizationType, String region, String group,
                                  StructureType structureType, ExtendedType extendedLstType,
                                  String extendedLstIdentifier, TagType extendedLstTagType,
                                  SelectorType extendedLstSelectorType, LinkType lstType,
                                  String parentLstIdentifier, TagType parentLstTagType,
                                  SelectorType parentLstSelectorType, String childLstIdentifier,
                                  TagType childLstTagType, SelectorType childLstSelectorType,
                                  int lstOrdinalNumber, LinkType yearType, String parentYearIdentifier,
                                  TagType parentYearTagType, SelectorType parentYearSelectorType,
                                  String childYearIdentifier, TagType childYearTagType,
                                  SelectorType childYearSelectorType, int yearOrdinalNumber,
                                  String pageUrl, int totalPage, ExtendedType extendedPdfType,
                                  String extendedPdfIdentifier, TagType extendedPdfTagType,
                                  SelectorType extendedPdfSelectorType, LinkType pdfType,
                                  String parentPdfIdentifier, TagType parentPdfTagType,
                                  SelectorType parentPdfSelectorType, String childPdfIdentifier,
                                  TagType childPdfTagType, SelectorType childPdfSelectorType,
                                  int pdfOrdinalNumber, TitleType titleType, String parentTitleIdentifier,
                                  TagType parentTitleTagType, SelectorType parentTitleSelectorType,
                                  String childTitleIdentifier, TagType childTitleTagType,
                                  SelectorType childTitleSelectorType, int titleOrdinalNumber,
                                  LinkType nextPageType, String parentNextPageIdentifier,
                                  TagType parentNextPageTagType, SelectorType parentNextPageSelectorType,
                                  String childNextPageIdentifier, TagType childNextPageTagType,
                                  SelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {

    public Target toEntity() {
        return new Target(organizationType, region, group, structureType, extendedLstType,
            extendedLstIdentifier, extendedLstTagType, extendedLstSelectorType, lstType, parentLstIdentifier,
            parentLstTagType, parentLstSelectorType, childLstIdentifier, childLstTagType,
            childLstSelectorType, lstOrdinalNumber, yearType, parentYearIdentifier, parentYearTagType,
            parentYearSelectorType, childYearIdentifier, childYearTagType, childYearSelectorType,
            yearOrdinalNumber, pageUrl, totalPage, extendedPdfType, extendedPdfIdentifier, extendedPdfTagType,
            extendedPdfSelectorType, pdfType, parentPdfIdentifier, parentPdfTagType, parentPdfSelectorType,
            childPdfIdentifier, childPdfTagType, childPdfSelectorType, pdfOrdinalNumber, titleType,
            parentTitleIdentifier, parentTitleTagType, parentTitleSelectorType, childTitleIdentifier,
            childTitleTagType, childTitleSelectorType, titleOrdinalNumber, nextPageType,
            parentNextPageIdentifier, parentNextPageTagType, parentNextPageSelectorType,
            childNextPageIdentifier, childNextPageTagType, childNextPageSelectorType, nextPageOrdinalNumber);
    }
}
