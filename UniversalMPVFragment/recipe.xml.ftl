<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <@kt.addAllKotlinDependencies />
    <instantiate from="root/res/layout/fragment_blank.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml" />

    <!-- fragment -->
    <instantiate from="root/src/app_package/OneFragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/ui/fragments/${fragmentClass}.${ktOrJavaExt}" />
    <!-- view -->
    <instantiate from="root/src/app_package/OneFragmentView.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/views/${fragmentClass}View.${ktOrJavaExt}" />
    <!-- presenter -->
    <instantiate from="root/src/app_package/OneFragmentPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenters/${fragmentClass}Presenter.${ktOrJavaExt}" />
</recipe>
