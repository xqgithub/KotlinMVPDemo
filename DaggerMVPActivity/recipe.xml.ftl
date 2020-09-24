<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

<#if generateLayout>
    <#include "../common/recipe_simple.xml.ftl" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

<!-- activity -->
    <instantiate from="root/src/app_package/TestProductFlavorsActivity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/ui/activities/${activityClass}Activity.${ktOrJavaExt}" />
<!-- View -->
    <instantiate from="root/src/app_package/ProductFlavorsView.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/views/${activityClass}View.${ktOrJavaExt}" />
<!-- Presenter -->
    <instantiate from="root/src/app_package/ProductFlavorsPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/presenters/${activityClass}Presenter.${ktOrJavaExt}" />

<!-- Scope -->
    <instantiate from="root/src/app_package/ProductFlavorsActivityScope.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/scopes/${activityClass}ActivityScope.${ktOrJavaExt}" />
<!-- Module -->
    <instantiate from="root/src/app_package/ProductFlavorsModule.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/modules/${activityClass}Module.${ktOrJavaExt}" />
<!-- Componet -->
    <instantiate from="root/src/app_package/ProductFlavorsComponet.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/componets/${activityClass}Componet.${ktOrJavaExt}" />

<open file="${escapeXmlAttribute(srcOut)}/mvp/ui/activities/${activityClass}.${ktOrJavaExt}" />
</recipe>
