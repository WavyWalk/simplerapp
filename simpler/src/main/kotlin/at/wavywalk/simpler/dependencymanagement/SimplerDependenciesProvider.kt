package at.wavywalk.simpler.dependencymanagement

import at.wavywalk.simpler.assetsmanagement.AssetsPathProvider
import at.wavywalk.simpler.utils.requestparameters.ServletRequestParametersWrapper
import at.wavywalk.simpler.utils.requestparameters.querystring.QueryStringParametersWrapper
import at.wavywalk.simpler.templating.ITemplateProcessor

open class SimplerDependenciesProvider(
    val servletRequestParametersWrapper: ServletRequestParametersWrapper,
    val templateProcessor: ITemplateProcessor,
    val assetsPathProvider: AssetsPathProvider,
    val queryStringParametersWrapper: QueryStringParametersWrapper = QueryStringParametersWrapper()
) {


}