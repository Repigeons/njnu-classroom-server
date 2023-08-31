package cn.repigeons.njnu.classroom.commons.config

import org.slf4j.TtlMDCAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(TtlMDCAdapter::class)
class TtlMDCAdapterAutoConfiguration