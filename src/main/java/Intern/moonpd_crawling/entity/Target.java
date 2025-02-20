package Intern.moonpd_crawling.entity;

import Intern.moonpd_crawling.dto.response.TargetViewResponse;
import Intern.moonpd_crawling.status.ExtendedPdfType;
import Intern.moonpd_crawling.status.LstType;
import Intern.moonpd_crawling.status.PdfType;
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
    @Column(name = "lst_type", nullable = true)
    private LstType lstType;

    @Column(name = "parent_lst_identifier", nullable = true)
    private String parentLstIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_lst_tag_type", nullable = true)
    private ParentLstTagType parentLstTagType;

    @Column(name = "child_lst_identifier", nullable = true)
    private String childLstIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_lst_tag_type", nullable = true)
    private ChildLstTagType childLstTagType;

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

    @Column(name = "child_pdf_identifier", nullable = true)
    private String childPdfIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_pdf_tag_type", nullable = true)
    private ChildPdfTagType childPdfTagType;

    @Column(name = "pdf_ordinal_number", nullable = true)
    private int pdfOrdinalNumber;

    // #############################################################################################

    @Column(name = "parent_title_identifier", nullable = true)
    private String parentTitleIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "parent_title_tag_type", nullable = true)
    private ParentTitleTagType parentTitleTagType;

    @Column(name = "child_title_identifier", nullable = true)
    private String childTitleIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_title_tag_type", nullable = true)
    private ChildTitleTagType childTitleTagType;

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

    @Column(name = "child_next_page_identifier", nullable = true)
    private String childNextPageIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_next_page_tag_type", nullable = true)
    private ChildNextPageTagType childNextPageTagType;

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
        StructureType structureType, LstType lstType, ParentLstTagType parentLstTagType,
        String parentLstIdentifier, ChildLstTagType childLstTagType, String childLstIdentifier,
        int lstOrdinalNumber, YearType yearType, String parentYearIdentifier,
        ParentYearTagType parentYearTagType, String childYearIdentifier,
        ChildYearTagType childYearTagType, int yearOrdinalNumber, String pageUrl,
        ExtendedPdfType extendedPdfType, String parentExtendedPdfIdentifier,
        ParentExtendedPdfTagType parentExtendedPdfTagType, int extendedPdfOrdinalNumber,
        PdfType pdfType, String parentPdfIdentifier, ParentPdfTagType parentPdfTagType,
        String childPdfIdentifier, ChildPdfTagType childPdfTagType, int pdfOrdinalNumber,
        String parentTitleIdentifier, ParentTitleTagType parentTitleTagType,
        String childTitleIdentifier, ChildTitleTagType childTitleTagType, int titleOrdinalNumber,
        NextPageType nextPageType, String parentNextPageIdentifier,
        ParentNextPageTagType parentNextPageTagType, String childNextPageIdentifier,
        ChildNextPageTagType childNextPageTagType, int nextPageOrdinalNumber) {
        this.organizationType = organizationType;
        this.region = region;
        this.group = group;
        this.structureType = structureType;
        this.lstType = lstType;
        this.parentLstTagType = parentLstTagType;
        this.parentLstIdentifier = parentLstIdentifier;
        this.childLstTagType = childLstTagType;
        this.childLstIdentifier = childLstIdentifier;
        this.lstOrdinalNumber = lstOrdinalNumber;
        this.yearType = yearType;
        this.parentYearIdentifier = parentYearIdentifier;
        this.parentYearTagType = parentYearTagType;
        this.childYearIdentifier = childYearIdentifier;
        this.childYearTagType = childYearTagType;
        this.yearOrdinalNumber = yearOrdinalNumber;
        this.pageUrl = pageUrl;
        this.extendedPdfType = extendedPdfType;
        this.parentExtendedPdfIdentifier = parentExtendedPdfIdentifier;
        this.parentExtendedPdfTagType = parentExtendedPdfTagType;
        this.extendedPdfOrdinalNumber = extendedPdfOrdinalNumber;
        this.pdfType = pdfType;
        this.parentPdfIdentifier = parentPdfIdentifier;
        this.parentPdfTagType = parentPdfTagType;
        this.childPdfIdentifier = childPdfIdentifier;
        this.childPdfTagType = childPdfTagType;
        this.pdfOrdinalNumber = pdfOrdinalNumber;
        this.titleOrdinalNumber = titleOrdinalNumber;
        this.parentTitleIdentifier = parentTitleIdentifier;
        this.parentTitleTagType = parentTitleTagType;
        this.childTitleIdentifier = childTitleIdentifier;
        this.childTitleTagType = childTitleTagType;
        this.nextPageType = nextPageType;
        this.parentNextPageIdentifier = parentNextPageIdentifier;
        this.parentNextPageTagType = parentNextPageTagType;
        this.childNextPageIdentifier = childNextPageIdentifier;
        this.childNextPageTagType = childNextPageTagType;
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
        this.lstType = target.getLstType();
        this.parentLstTagType = target.getParentLstTagType();
        this.parentLstIdentifier = target.getParentLstIdentifier();
        this.childLstTagType = target.getChildLstTagType();
        this.childLstIdentifier = target.getChildLstIdentifier();
        this.lstOrdinalNumber = target.getLstOrdinalNumber();
        this.yearType = target.getYearType();
        this.parentYearIdentifier = target.getParentYearIdentifier();
        this.parentYearTagType = target.getParentYearTagType();
        this.childYearIdentifier = target.getChildYearIdentifier();
        this.childYearTagType = target.getChildYearTagType();
        this.yearOrdinalNumber = target.getYearOrdinalNumber();
        this.pageUrl = target.getPageUrl();
        this.extendedPdfType = target.getExtendedPdfType();
        this.parentExtendedPdfIdentifier = target.getParentExtendedPdfIdentifier();
        this.parentExtendedPdfTagType = target.getParentExtendedPdfTagType();
        this.extendedPdfOrdinalNumber = target.getExtendedPdfOrdinalNumber();
        this.pdfType = target.getPdfType();
        this.parentPdfIdentifier = target.getParentPdfIdentifier();
        this.parentPdfTagType = target.getParentPdfTagType();
        this.childPdfIdentifier = target.getChildPdfIdentifier();
        this.childPdfTagType = target.getChildPdfTagType();
        this.pdfOrdinalNumber = target.getPdfOrdinalNumber();
        this.titleOrdinalNumber = target.getTitleOrdinalNumber();
        this.parentTitleIdentifier = target.getParentTitleIdentifier();
        this.parentTitleTagType = target.getParentTitleTagType();
        this.childTitleIdentifier = target.getChildTitleIdentifier();
        this.childTitleTagType = target.getChildTitleTagType();
        this.nextPageType = target.getNextPageType();
        this.parentNextPageIdentifier = target.getParentNextPageIdentifier();
        this.parentNextPageTagType = target.getParentNextPageTagType();
        this.childNextPageIdentifier = target.getChildNextPageIdentifier();
        this.childNextPageTagType = target.getChildNextPageTagType();
        this.nextPageOrdinalNumber = target.getNextPageOrdinalNumber();
    }

    public TargetViewResponse toDto() {
        return new TargetViewResponse(this.id, this.region, this.group);
    }
}
