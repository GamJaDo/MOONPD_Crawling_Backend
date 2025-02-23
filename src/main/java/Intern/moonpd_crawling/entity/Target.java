package Intern.moonpd_crawling.entity;

import Intern.moonpd_crawling.dto.response.TargetViewResponse;
import Intern.moonpd_crawling.status.selector.child.ChildLstSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildPdfSelectorType;
import Intern.moonpd_crawling.status.selector.child.ChildTitleSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentLstSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentNextPageSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentPdfSelectorType;
import Intern.moonpd_crawling.status.selector.parent.ParentTitleSelectorType;
import Intern.moonpd_crawling.status.tag.parent.ParentExtendedLstTagType;
import Intern.moonpd_crawling.status.type.ExtendedLstType;
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
    private ExtendedLstType extendedLstType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_extended_lst_tag_type", nullable = true)
    private ParentExtendedLstTagType parentExtendedLstTagType;

    @Column(name = "parent_extended_lst_identifier", nullable = true)
    private String parentExtendedLstIdentifier;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "lst_type", nullable = true)
    private LstType lstType;

    @Column(name = "parent_lst_identifier", nullable = true)
    private String parentLstIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_lst_tag_type", nullable = true)
    private ParentLstTagType parentLstTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_lst_selector_type", nullable = true)
    private ParentLstSelectorType parentLstSelectorType;

    @Column(name = "child_lst_identifier", nullable = true)
    private String childLstIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_lst_tag_type", nullable = true)
    private ChildLstTagType childLstTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_lst_selector_type", nullable = true)
    private ChildLstSelectorType childLstSelectorType;

    @Column(name = "lst_ordinal_number", nullable = true)
    private int lstOrdinalNumber;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "year_type", nullable = true)
    private YearType yearType;

    @Column(name = "parent_year_identifier", nullable = true)
    private String parentYearIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_year_tag_type", nullable = true)
    private ParentYearTagType parentYearTagType;

    @Column(name = "child_year_identifier", nullable = true)
    private String childYearIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_year_tag_type", nullable = true)
    private ChildYearTagType childYearTagType;

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
    private ExtendedPdfType extendedPdfType;

    @Column(name = "parent_extended_pdf_identifier", nullable = true)
    private String parentExtendedPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_extended_pdf_tag_type", nullable = true)
    private ParentExtendedPdfTagType parentExtendedPdfTagType;

    @Column(name = "extended_pdf_ordinal_number", nullable = true)
    private int extendedPdfOrdinalNumber;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "pdf_type", nullable = true)
    private PdfType pdfType;

    @Column(name = "parent_pdf_identifier", nullable = true)
    private String parentPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_pdf_tag_type", nullable = true)
    private ParentPdfTagType parentPdfTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_pdf_selector_type", nullable = true)
    private ParentPdfSelectorType parentPdfSelectorType;

    @Column(name = "child_pdf_identifier", nullable = true)
    private String childPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_pdf_tag_type", nullable = true)
    private ChildPdfTagType childPdfTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_pdf_selector_type", nullable = true)
    private ChildPdfSelectorType childPdfSelectorType;

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
    private ParentTitleTagType parentTitleTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_title_selector_type", nullable = true)
    private ParentTitleSelectorType parentTitleSelectorType;

    @Column(name = "child_title_identifier", nullable = true)
    private String childTitleIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_title_tag_type", nullable = true)
    private ChildTitleTagType childTitleTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_title_selector_type", nullable = true)
    private ChildTitleSelectorType childTitleSelectorType;

    @Column(name = "title_ordinal_number", nullable = true)
    private int titleOrdinalNumber;

    // #############################################################################################

    @Enumerated(EnumType.STRING)
    @Column(name = "next_page_type", nullable = true)
    private NextPageType nextPageType;

    @Column(name = "parent_next_page_identifier", nullable = true)
    private String parentNextPageIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_next_page_tag_type", nullable = true)
    private ParentNextPageTagType parentNextPageTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_next_page_selector_type", nullable = true)
    private ParentNextPageSelectorType parentNextPageSelectorType;

    @Column(name = "child_next_page_identifier", nullable = true)
    private String childNextPageIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_next_page_tag_type", nullable = true)
    private ChildNextPageTagType childNextPageTagType;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_next_page_selector_type", nullable = true)
    private ChildNextPageSelectorType childNextPageSelectorType;

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

    public Target(OrganizationType organizationType, String region, String group,
        StructureType structureType, ExtendedLstType extendedLstType,
        ParentExtendedLstTagType parentExtendedLstTagType,
        String parentExtendedLstIdentifier, LstType lstType, String parentLstIdentifier,
        ParentLstTagType parentLstTagType, ParentLstSelectorType parentLstSelectorType,
        String childLstIdentifier, ChildLstTagType childLstTagType,
        ChildLstSelectorType childLstSelectorType, int lstOrdinalNumber, YearType yearType,
        String parentYearIdentifier, ParentYearTagType parentYearTagType,
        String childYearIdentifier,
        ChildYearTagType childYearTagType, int yearOrdinalNumber, String pageUrl, int totalPage,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        ParentPdfSelectorType parentPdfSelectorType, String childPdfIdentifier,
        ChildPdfTagType childPdfTagType, ChildPdfSelectorType childPdfSelectorType,
        int pdfOrdinalNumber, TitleType titleType, String parentTitleIdentifier,
        ParentTitleTagType parentTitleTagType, ParentTitleSelectorType parentTitleSelectorType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType,
        ChildTitleSelectorType childTitleSelectorType, int titleOrdinalNumber,
        NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType,
        ParentNextPageSelectorType parentNextPageSelectorType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType,
        ChildNextPageSelectorType childNextPageSelectorType, int nextPageOrdinalNumber) {
        this.organizationType = organizationType;
        this.region = region;
        this.group = group;
        this.structureType = structureType;
        this.extendedLstType = extendedLstType;
        this.parentExtendedLstTagType = parentExtendedLstTagType;
        this.parentExtendedLstIdentifier = parentExtendedLstIdentifier;
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
        this.childYearIdentifier = childYearIdentifier;
        this.childYearTagType = childYearTagType;
        this.yearOrdinalNumber = yearOrdinalNumber;
        this.pageUrl = pageUrl;
        this.totalPage = totalPage;
        this.extendedPdfType = extendedPdfType;
        this.parentExtendedPdfIdentifier = parentExtendedPdfIdentifier;
        this.parentExtendedPdfTagType = parentExtendedPdfTagType;
        this.extendedPdfOrdinalNumber = extendedPdfOrdinalNumber;
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
        this.parentExtendedLstTagType = target.getParentExtendedLstTagType();
        this.parentExtendedLstIdentifier = target.getParentExtendedLstIdentifier();
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
        this.childYearIdentifier = target.getChildYearIdentifier();
        this.childYearTagType = target.getChildYearTagType();
        this.yearOrdinalNumber = target.getYearOrdinalNumber();
        this.pageUrl = target.getPageUrl();
        this.totalPage = target.getTotalPage();
        this.extendedPdfType = target.getExtendedPdfType();
        this.parentExtendedPdfIdentifier = target.getParentExtendedPdfIdentifier();
        this.parentExtendedPdfTagType = target.getParentExtendedPdfTagType();
        this.extendedPdfOrdinalNumber = target.getExtendedPdfOrdinalNumber();
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
