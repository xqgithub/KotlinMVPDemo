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
                   to="${escapeXmlAttribute(srcOut)}/mvp/ui/activities/${activityClass}.${ktOrJavaExt}" />
    <!-- View -->
    <instantiate from="root/src/app_package/ProductFlavorsView.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/views/${subordinate}View.${ktOrJavaExt}" />
    <!-- Presenter -->
    <instantiate from="root/src/app_package/ProductFlavorsPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/presenters/${subordinate}Presenter.${ktOrJavaExt}" />
    <!-- module -->
    <instantiate from="root/src/app_package/ProductFlavorsModule.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/modules/${subordinate}Module.${ktOrJavaExt}" />
    <!-- componet -->
    <instantiate from="root/src/app_package/ProductFlavorsComponet.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/componets/${subordinate}Componet.${ktOrJavaExt}" />
    <!-- Scope -->
    <instantiate from="root/src/app_package/ProductFlavorsActivityScope.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/scopes/${activityClass}Scope.${ktOrJavaExt}" />
</recipe>
