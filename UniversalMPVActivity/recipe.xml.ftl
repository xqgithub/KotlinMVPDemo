<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

<#if generateLayout || (includeCppSupport!false)>
    <#include "../common/recipe_simple.xml.ftl" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

    <!-- Activity -->
    <instantiate from="root/src/app_package/TestProductFlavorsActivity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/ui/activities/${activityClass}.${ktOrJavaExt}" />
    <!-- View -->
    <instantiate from="root/src/app_package/ProductFlavorsView.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/ui/views/${subordinate}View.${ktOrJavaExt}" />
    <!-- Presenter -->
    <instantiate from="root/src/app_package/ProductFlavorsPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/ui/presenters/${subordinate}Presenter.${ktOrJavaExt}" />
</recipe>
