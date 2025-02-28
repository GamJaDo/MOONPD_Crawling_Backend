package Intern.moonpd_crawling.entity;

import Intern.moonpd_crawling.dto.response.TargetViewResponse;
import Intern.moonpd_crawling.status.type.SelectorType;
import Intern.moonpd_crawling.status.type.TagType;
import Intern.moonpd_crawling.status.type.ExtendedType;
import Intern.moonpd_crawling.status.type.LinkType;
import Intern.moonpd_crawling.status.type.TitleType;
import Intern.moonpd_crawling.status.type.OrganizationType;
import Intern.moonpd_crawling.status.type.StructureType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "Target_TB")
@Getter
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type", nullable = true)
    private OrganizationType organizationType;

    @Column(name = "region", nullable = true)
    private String region;

    @Column(name = "`group`", nullable = true)
    private String group;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "structure_type", nullable = true)
    private StructureType structureType;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "extended_lst_type", nullable = true)
    private ExtendedType extendedLstType;

    @Enumerated(EnumType.STRING)
    @Column(name = "extended_lst_tag_type", nullable = true)
    private TagType extendedLstTagType;

    @Column(name = "extended_lst_identifier", nullable = true)
    private String extendedLstIdentifier;

    @Column(name = "extended_lst_selector_type", nullable = true)
    private SelectorType extendedLstSelectorType;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "lst_type", nullable = true)
    private LinkType lstType;

    @Column(name = "parent_lst_identifier", nullable = true)
    private String parentLstIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_lst_tag_type", nullable = true)
    private TagType parentLstTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_lst_selector_type", nullable = true)
    private SelectorType parentLstSelectorType;

    @Column(name = "child_lst_identifier", nullable = true)
    private String childLstIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_lst_tag_type", nullable = true)
    private TagType childLstTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_lst_selector_type", nullable = true)
    private SelectorType childLstSelectorType;

    @Column(name = "lst_ordinal_number", nullable = true)
    private int lstOrdinalNumber;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "year_type", nullable = true)
    private LinkType yearType;

    @Column(name = "parent_year_identifier", nullable = true)
    private String parentYearIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_year_tag_type", nullable = true)
    private TagType parentYearTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_year_selector_type", nullable = true)
    private SelectorType parentYearSelectorType;

    @Column(name = "child_year_identifier", nullable = true)
    private String childYearIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_year_tag_type", nullable = true)
    private TagType childYearTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_year_selector_type", nullable = true)
    private SelectorType childYearSelectorType;

    @Column(name = "year_ordinal_number", nullable = true)
    private int yearOrdinalNumber;

    // #############################################################################################

    @Lob
    @Column(name = "page_url", nullable = true)
    private String pageUrl;

    @Column(name = "total_page", nullable = true)
    private int totalPage;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "extended_pdf_type", nullable = true)
    private ExtendedType extendedPdfType;

    @Column(name = "extended_pdf_identifier", nullable = true)
    private String extendedPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "extended_pdf_tag_type", nullable = true)
    private TagType extendedPdfTagType;

    @Column(name = "extended_pdf_selector_type", nullable = true)
    private SelectorType extendedPdfSelectorType;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "pdf_type", nullable = true)
    private LinkType pdfType;

    @Column(name = "parent_pdf_identifier", nullable = true)
    private String parentPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_pdf_tag_type", nullable = true)
    private TagType parentPdfTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_pdf_selector_type", nullable = true)
    private SelectorType parentPdfSelectorType;

    @Column(name = "child_pdf_identifier", nullable = true)
    private String childPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_pdf_tag_type", nullable = true)
    private TagType childPdfTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_pdf_selector_type", nullable = true)
    private SelectorType childPdfSelectorType;

    @Column(name = "pdf_ordinal_number", nullable = true)
    private int pdfOrdinalNumber;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "title_type", nullable = true)
    private TitleType titleType;

    @Column(name = "parent_title_identifier", nullable = true)
    private String parentTitleIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_title_tag_type", nullable = true)
    private TagType parentTitleTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_title_selector_type", nullable = true)
    private SelectorType parentTitleSelectorType;

    @Column(name = "child_title_identifier", nullable = true)
    private String childTitleIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_title_tag_type", nullable = true)
    private TagType childTitleTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_title_selector_type", nullable = true)
    private SelectorType childTitleSelectorType;

    @Column(name = "title_ordinal_number", nullable = true)
    private int titleOrdinalNumber;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "next_page_type", nullable = true)
    private LinkType nextPageType;

    @Column(name = "parent_next_page_identifier", nullable = true)
    private String parentNextPageIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_next_page_tag_type", nullable = true)
    private TagType parentNextPageTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_next_page_selector_type", nullable = true)
    private SelectorType parentNextPageSelectorType;

    @Column(name = "child_next_page_identifier", nullable = true)
    private String childNextPageIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_next_page_tag_type", nullable = true)
    private TagType childNextPageTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_next_page_selector_type", nullable = true)
    private SelectorType childNextPageSelectorType;

    @Column(name = "next_page_ordinal_number", nullable = true)
    private int nextPageOrdinalNumber;

    // #############################################################################################

    @CreatedDate
    @Column(name = "register_time", nullable = true)
    private LocalDateTime registerTime = null;

    @CreatedDate
    @Column(name = "update_time", nullable = true)
    private LocalDateTime updateTime = null;

    protected Target() {
    }

    public Target(OrganizationType organizationType, String region, String group, StructureType structureType,
        ExtendedType extendedLstType, String extendedLstIdentifier, TagType extendedLstTagType,
        SelectorType extendedLstSelectorType, LinkType lstType, String parentLstIdentifier,
        TagType parentLstTagType, SelectorType parentLstSelectorType, String childLstIdentifier,
        TagType childLstTagType, SelectorType childLstSelectorType, int lstOrdinalNumber, LinkType yearType,
        String parentYearIdentifier, TagType parentYearTagType, SelectorType parentYearSelectorType,
        String childYearIdentifier, TagType childYearTagType, SelectorType childYearSelectorType,
        int yearOrdinalNumber, String pageUrl, int totalPage, ExtendedType extendedPdfType,
        String extendedPdfIdentifier, TagType extendedPdfTagType, SelectorType extendedPdfSelectorType,
        LinkType pdfType, String parentPdfIdentifier, TagType parentPdfTagType,
        SelectorType parentPdfSelectorType, String childPdfIdentifier, TagType childPdfTagType,
        SelectorType childPdfSelectorType, int pdfOrdinalNumber, TitleType titleType,
        String parentTitleIdentifier, TagType parentTitleTagType, SelectorType parentTitleSelectorType,
        String childTitleIdentifier, TagType childTitleTagType, SelectorType childTitleSelectorType,
        int titleOrdinalNumber, LinkType nextPageType, String parentNextPageIdentifier,
        TagType parentNextPageTagType, SelectorType parentNextPageSelectorType,
        String childNextPageIdentifier, TagType childNextPageTagType, SelectorType childNextPageSelectorType,
        int nextPageOrdinalNumber) {
        this.organizationType = organizationType;
        this.region = region;
        this.group = group;
        this.structureType = structureType;
        this.extendedLstType = extendedLstType;
        this.extendedLstIdentifier = extendedLstIdentifier;
        this.extendedLstTagType = extendedLstTagType;
        this.extendedLstSelectorType = extendedLstSelectorType;
        this.lstType = lstType;
        this.parentLstIdentifier = parentLstIdentifier;
        this.parentLstTagType = parentLstTagType;
        this.parentLstSelectorType = parentLstSelectorType;
        this.childLstIdentifier = childLstIdentifier;
        this.childLstTagType = childLstTagType;
        this.childLstSelectorType = childLstSelectorType;
        this.lstOrdinalNumber = lstOrdinalNumber;
        this.yearType = yearType;
        this.parentYearIdentifier = parentYearIdentifier;
        this.parentYearTagType = parentYearTagType;
        this.parentYearSelectorType = parentYearSelectorType;
        this.childYearIdentifier = childYearIdentifier;
        this.childYearTagType = childYearTagType;
        this.childYearSelectorType = childYearSelectorType;
        this.yearOrdinalNumber = yearOrdinalNumber;
        this.pageUrl = pageUrl;
        this.totalPage = totalPage;
        this.extendedPdfType = extendedPdfType;
        this.extendedPdfIdentifier = extendedPdfIdentifier;
        this.extendedPdfTagType = extendedPdfTagType;
        this.extendedPdfSelectorType = extendedPdfSelectorType;
        this.pdfType = pdfType;
        this.parentPdfIdentifier = parentPdfIdentifier;
        this.parentPdfTagType = parentPdfTagType;
        this.parentPdfSelectorType = parentPdfSelectorType;
        this.childPdfIdentifier = childPdfIdentifier;
        this.childPdfTagType = childPdfTagType;
        this.childPdfSelectorType = childPdfSelectorType;
        this.pdfOrdinalNumber = pdfOrdinalNumber;
        this.titleType = titleType;
        this.titleOrdinalNumber = titleOrdinalNumber;
        this.parentTitleIdentifier = parentTitleIdentifier;
        this.parentTitleTagType = parentTitleTagType;
        this.parentTitleSelectorType = parentTitleSelectorType;
        this.childTitleIdentifier = childTitleIdentifier;
        this.childTitleTagType = childTitleTagType;
        this.childTitleSelectorType = childTitleSelectorType;
        this.nextPageType = nextPageType;
        this.parentNextPageIdentifier = parentNextPageIdentifier;
        this.parentNextPageTagType = parentNextPageTagType;
        this.parentNextPageSelectorType = parentNextPageSelectorType;
        this.childNextPageIdentifier = childNextPageIdentifier;
        this.childNextPageTagType = childNextPageTagType;
        this.childNextPageSelectorType = childNextPageSelectorType;
        this.nextPageOrdinalNumber = nextPageOrdinalNumber;
    }

    public void setRegisterTime() {
        this.registerTime = LocalDateTime.now();
    }

    public void setUpdateTime() {
        this.updateTime = LocalDateTime.now();
    }

    public void update(Target target) {
        this.organizationType = target.getOrganizationType();
        this.region = target.getRegion();
        this.group = target.getGroup();
        this.structureType = target.getStructureType();
        this.extendedLstType = target.getExtendedLstType();
        this.extendedLstTagType = target.getExtendedLstTagType();
        this.extendedLstIdentifier = target.getExtendedLstIdentifier();
        this.extendedLstSelectorType = target.getExtendedLstSelectorType();
        this.lstType = target.getLstType();
        this.parentLstIdentifier = target.getParentLstIdentifier();
        this.parentLstTagType = target.getParentLstTagType();
        this.parentLstSelectorType = target.getParentLstSelectorType();
        this.childLstIdentifier = target.getChildLstIdentifier();
        this.childLstTagType = target.getChildLstTagType();
        this.childLstSelectorType = target.getChildLstSelectorType();
        this.lstOrdinalNumber = target.getLstOrdinalNumber();
        this.yearType = target.getYearType();
        this.parentYearIdentifier = target.getParentYearIdentifier();
        this.parentYearTagType = target.getParentYearTagType();
        this.parentYearSelectorType = target.getParentYearSelectorType();
        this.childYearIdentifier = target.getChildYearIdentifier();
        this.childYearTagType = target.getChildYearTagType();
        this.childYearSelectorType = target.getChildYearSelectorType();
        this.yearOrdinalNumber = target.getYearOrdinalNumber();
        this.pageUrl = target.getPageUrl();
        this.totalPage = target.getTotalPage();
        this.extendedPdfType = target.getExtendedPdfType();
        this.extendedPdfIdentifier = target.getExtendedPdfIdentifier();
        this.extendedPdfSelectorType = target.getExtendedPdfSelectorType();
        this.extendedPdfTagType = target.getExtendedPdfTagType();
        this.pdfType = target.getPdfType();
        this.parentPdfIdentifier = target.getParentPdfIdentifier();
        this.parentPdfTagType = target.getParentPdfTagType();
        this.parentPdfSelectorType = target.getParentPdfSelectorType();
        this.childPdfIdentifier = target.getChildPdfIdentifier();
        this.childPdfTagType = target.getChildPdfTagType();
        this.childPdfSelectorType = target.getChildPdfSelectorType();
        this.pdfOrdinalNumber = target.getPdfOrdinalNumber();
        this.titleType = target.getTitleType();
        this.parentTitleIdentifier = target.getParentTitleIdentifier();
        this.parentTitleTagType = target.getParentTitleTagType();
        this.parentTitleSelectorType = target.getParentTitleSelectorType();
        this.childTitleIdentifier = target.getChildTitleIdentifier();
        this.childTitleTagType = target.getChildTitleTagType();
        this.childTitleSelectorType = target.getChildTitleSelectorType();
        this.titleOrdinalNumber = target.getTitleOrdinalNumber();
        this.nextPageType = target.getNextPageType();
        this.parentNextPageIdentifier = target.getParentNextPageIdentifier();
        this.parentNextPageTagType = target.getParentNextPageTagType();
        this.parentNextPageSelectorType = target.getParentNextPageSelectorType();
        this.childNextPageIdentifier = target.getChildNextPageIdentifier();
        this.childNextPageTagType = target.getChildNextPageTagType();
        this.childNextPageSelectorType = target.getChildNextPageSelectorType();
        this.nextPageOrdinalNumber = target.getNextPageOrdinalNumber();
    }

    public TargetViewResponse toDto() {
        return new TargetViewResponse(this.id, this.region, this.group);
    }
}
